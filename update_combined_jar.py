import zipfile, os, shutil

combined_mods_toml = """modLoader="javafml"
loaderVersion="[47,)"
license="GNU GPLv3"

[[mods]]
modId="reignofnether"
version="1.0.2"
displayName="Reign of Nether Overhaul"
logoFile="examplemod.png"
credits="Thanks to Technovision for his YouTube guides"
authors="SoLegendary, Goodbird, Codex"
description='''
Minecraft, except it's an RTS (Overhaul Version 1.0.2)
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
version="1.0.2"
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

[[dependencies.ron_golem_healer_integration]]
modId="reignofnether"
mandatory=true
versionRange="[1.0.2,)"
ordering="AFTER"
side="BOTH"

[[dependencies.ron_golem_healer_integration]]
modId="villagergolemhealer"
mandatory=true
versionRange="[1.0.0,)"
ordering="AFTER"
side="BOTH"

[[dependencies.ron_golem_healer_integration]]
modId="guardillagers"
mandatory=true
versionRange="[1.0.1,)"
ordering="AFTER"
side="BOTH"

[[dependencies.ron_golem_healer_integration]]
modId="slash_illager"
mandatory=true
versionRange="[1.0.0,)"
ordering="AFTER"
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

base_jar = os.path.join(base_dir, 'reignofnether_base.jar')
target_102_jar = os.path.join(base_dir, 'reignofnether-overhaul-1.0.2.jar')
target_101_jar = os.path.join(base_dir, 'reignofnether-overhaul-1.0.1.jar')

if os.path.exists(base_jar):
    shutil.copy(base_jar, target_102_jar)

update_jar(target_102_jar)
update_jar(target_101_jar)
update_jar(base_jar)

mc_mods_dir = 'C:/Users/User/AppData/Roaming/.minecraft/mods'
mc_102 = os.path.join(mc_mods_dir, 'reignofnether-overhaul-1.0.2.jar')

if os.path.exists(mc_mods_dir):
    # Remove old version jars (26.1, 1.0.1)
    for old_jar_name in ['reignofnether-overhaul-26.1.jar', 'reignofnether-overhaul-1.0.1.jar']:
        old_path = os.path.join(mc_mods_dir, old_jar_name)
        if os.path.exists(old_path):
            try:
                os.remove(old_path)
                print(f'Removed old {old_jar_name} from {mc_mods_dir}')
            except Exception as e:
                print(f'Note: Could not remove {old_jar_name}: {e}')

    try:
        shutil.copy(target_102_jar, mc_102)
        print(f'Copied reignofnether-overhaul-1.0.2.jar to {mc_mods_dir}')
    except Exception as e:
        print(f'Note: Could not copy 1.0.2 jar: {e}')
