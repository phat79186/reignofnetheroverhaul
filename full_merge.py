import zipfile, os

integration_jar = 'c:/Users/User/Desktop/New folder (7)/ron-golem-healer-integration-1.20.1-1.0.0.jar'

# 1. Read all files from ron-golem-healer-integration-1.20.1-1.0.0.jar
integration_files = {}
with zipfile.ZipFile(integration_jar, 'r') as z:
    for item in z.infolist():
        if not item.filename.startswith('META-INF/'):
            integration_files[item.filename] = z.read(item.filename)

# 2. Read compiled codex class files from workspace (which contain our new Enchanter & null-safe edits)
for root, dirs, files in os.walk('c:/Users/User/Desktop/New folder (7)/com/codex'):
    for f in files:
        full = os.path.join(root, f)
        rel = os.path.relpath(full, 'c:/Users/User/Desktop/New folder (7)').replace('\\', '/')
        integration_files[rel] = open(full, 'rb').read()

# 3. Read compiled MusicManagerMixin from workspace
music_mixin_bytes = open('c:/Users/User/Desktop/New folder (7)/decompiled_ron/com/solegendary/reignofnether/mixin/MusicManagerMixin.class', 'rb').read()

# 4. Perform complete merge into reignofnether jars
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

def merge_into_ron(jar_path):
    if not os.path.exists(jar_path):
        return
    temp_path = jar_path + '.tmp'
    with zipfile.ZipFile(jar_path, 'r') as zin:
        existing = {item.filename for item in zin.infolist()}
        with zipfile.ZipFile(temp_path, 'w') as zout:
            for item in zin.infolist():
                if item.filename == 'com/solegendary/reignofnether/mixin/MusicManagerMixin.class':
                    zout.writestr(item.filename, music_mixin_bytes)
                elif item.filename == 'META-INF/mods.toml':
                    zout.writestr(item.filename, combined_mods_toml.encode('utf-8'))
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
                    zout.writestr(item.filename, manifest_str.encode('utf-8'))
                else:
                    zout.writestr(item, zin.read(item.filename))
            
            # Write all integration files (assets, refmaps, mixins, textures, lang files, mobheads, animmodels)
            for path, data in integration_files.items():
                if path not in existing or path.startswith('com/codex/') or path == 'ron_golem_healer_integration.mixins.json':
                    zout.writestr(path, data)
                    
    os.replace(temp_path, jar_path)
    print(f'Full merge completed into {jar_path}')

merge_into_ron('c:/Users/User/Desktop/New folder (7)/reignofnether-1.3.6b.jar')
merge_into_ron('c:/Users/User/Desktop/New folder (7)/reignofnether_base.jar')
merge_into_ron('C:/Users/User/AppData/Roaming/.minecraft/mods/reignofnether-1.3.6b.jar')
