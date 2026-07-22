import zipfile, os

combined_mods_toml = """modLoader="javafml"
loaderVersion="[47,)"
license="GNU GPLv3"

[[mods]]
modId="reignofnether"
version="1.3.6b"
displayName="Reign of Nether"
logoFile="examplemod.png"
credits="Thanks to Technovision for his YouTube guides"
authors="SoLegendary, Goodbird (ported to 1.20.1)"
description='''
Minecraft, except it's an RTS
'''

[[dependencies.reignofnether]]
    modId="forge"
    mandatory=true
    versionRange="[47,)"
    ordering="NONE"
    side="BOTH"

[[dependencies.reignofnether]]
    modId="minecraft"
    mandatory=true
    versionRange="[1.20.1,1.21)"
    ordering="NONE"
    side="BOTH"

[[mods]]
modId="ron_golem_healer_integration"
version="1.0.0"
displayName="RoN Golem Healer Integration"
authors="Codex"
description='''Integrates the original Villager Golem Healer entity as a Reign of Nether unit.'''

[[dependencies.ron_golem_healer_integration]]
modId="forge"
mandatory=true
versionRange="[47.4.0,)"
ordering="NONE"
side="BOTH"

[[dependencies.ron_golem_healer_integration]]
modId="minecraft"
mandatory=true
versionRange="[1.20.1,1.21)"
ordering="NONE"
side="BOTH"
"""

codex_files = {}
for root, dirs, files in os.walk('c:/Users/User/Desktop/New folder (7)/com/codex'):
    for f in files:
        full = os.path.join(root, f)
        rel = os.path.relpath(full, 'c:/Users/User/Desktop/New folder (7)')
        rel_zip = rel.replace('\\', '/')
        codex_files[rel_zip] = open(full, 'rb').read()

mixin_json_data = open('c:/Users/User/Desktop/New folder (7)/ron_golem_healer_integration.mixins.json', 'rb').read()

def update_jar(jar_path):
    if not os.path.exists(jar_path):
        return
    temp_path = jar_path + '.tmp'
    with zipfile.ZipFile(jar_path, 'r') as zin:
        with zipfile.ZipFile(temp_path, 'w') as zout:
            for item in zin.infolist():
                if item.filename.startswith('com/codex/') or item.filename == 'ron_golem_healer_integration.mixins.json':
                    continue
                if item.filename == 'META-INF/mods.toml':
                    zout.writestr(item, combined_mods_toml.encode('utf-8'))
                elif item.filename == 'META-INF/MANIFEST.MF':
                    manifest_str = zin.read(item.filename).decode('utf-8')
                    if 'ron_golem_healer_integration.mixins.json' not in manifest_str:
                        lines = manifest_str.splitlines()
                        new_lines = []
                        for line in lines:
                            if line.startswith('MixinConfigs:'):
                                line = line + ',ron_golem_healer_integration.mixins.json'
                            new_lines.append(line)
                        manifest_str = '\n'.join(new_lines) + '\n'
                    zout.writestr(item, manifest_str.encode('utf-8'))
                else:
                    zout.writestr(item, zin.read(item.filename))
            
            for path, data in codex_files.items():
                zout.writestr(path, data)
            zout.writestr('ron_golem_healer_integration.mixins.json', mixin_json_data)

    try:
        os.replace(temp_path, jar_path)
        print(f'Successfully updated combined jar: {jar_path}')
    except PermissionError:
        print(f'Warning: Could not overwrite {jar_path} because it is currently in use (Minecraft might be running).')
        if os.path.exists(temp_path):
            os.remove(temp_path)

update_jar('c:/Users/User/Desktop/New folder (7)/reignofnether-overhaul-1.0.1.jar')
update_jar('c:/Users/User/Desktop/New folder (7)/reignofnether-1.3.6b.jar')
update_jar('c:/Users/User/Desktop/New folder (7)/reignofnether_base.jar')
update_jar('C:/Users/User/AppData/Roaming/.minecraft/mods/reignofnether-1.3.6b.jar')
if os.path.exists('C:/Users/User/AppData/Roaming/.minecraft/mods/reignofnether-overhaul-1.0.1.jar'):
    update_jar('C:/Users/User/AppData/Roaming/.minecraft/mods/reignofnether-overhaul-1.0.1.jar')
try:
    import shutil
    shutil.copy('c:/Users/User/Desktop/New folder (7)/reignofnether-overhaul-1.0.1.jar', 'C:/Users/User/AppData/Roaming/.minecraft/mods/')
    print('Copied reignofnether-overhaul-1.0.1.jar to .minecraft/mods')
except Exception as e:
    print(f'Note: Could not copy jar to .minecraft/mods: {e}')
