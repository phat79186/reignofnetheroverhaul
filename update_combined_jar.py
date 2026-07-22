import zipfile, os, shutil

combined_mods_toml = """modLoader="javafml"
loaderVersion="[47,)"
license="GNU GPLv3"

[[mods]]
modId="reignofnether"
version="26.1"
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
version="26.1"
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
base_dir = os.path.dirname(os.path.abspath(__file__))
com_codex_dir = os.path.join(base_dir, 'com', 'codex')

for root, dirs, files in os.walk(com_codex_dir):
    for f in files:
        full = os.path.join(root, f)
        rel = os.path.relpath(full, base_dir)
        rel_zip = rel.replace('\\', '/')
        codex_files[rel_zip] = open(full, 'rb').read()

mixin_json_path = os.path.join(base_dir, 'ron_golem_healer_integration.mixins.json')
mixin_json_data = open(mixin_json_path, 'rb').read()

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
        print(f'Warning: Could not overwrite {jar_path} because it is currently in use.')
        if os.path.exists(temp_path):
            os.remove(temp_path)

# Prepare 26.1 jar based on base jar or existing jar
base_jar = os.path.join(base_dir, 'reignofnether_base.jar')
target_261_jar = os.path.join(base_dir, 'reignofnether-overhaul-26.1.jar')
target_101_jar = os.path.join(base_dir, 'reignofnether-overhaul-1.0.1.jar')

if os.path.exists(base_jar):
    shutil.copy(base_jar, target_261_jar)
    shutil.copy(base_jar, target_101_jar)

update_jar(target_261_jar)
update_jar(target_101_jar)
update_jar(base_jar)

mc_mods_dir = 'C:/Users/User/AppData/Roaming/.minecraft/mods'
mc_261 = os.path.join(mc_mods_dir, 'reignofnether-overhaul-26.1.jar')
mc_101 = os.path.join(mc_mods_dir, 'reignofnether-overhaul-1.0.1.jar')

if os.path.exists(mc_mods_dir):
    try:
        shutil.copy(target_261_jar, mc_261)
        print(f'Copied reignofnether-overhaul-26.1.jar to {mc_mods_dir}')
    except Exception as e:
        print(f'Note: Could not copy 26.1 jar: {e}')

    try:
        if os.path.exists(mc_101):
            os.remove(mc_101)
            print(f'Removed old reignofnether-overhaul-1.0.1.jar from {mc_mods_dir}')
    except Exception as e:
        print(f'Note: Could not remove old 1.0.1 jar: {e}')
