from os.path import dirname, abspath
from zipfile import ZipFile
import platform
import json

opsys = platform.system()
mod_path = dirname(abspath(__file__))
mod_name = ""
utility = False

if opsys.lower() == "windows":
    stripped_path = mod_path.split("\\")
    mod_name = stripped_path[-1]

if opsys.lower() == "linux":
    stripped_path = mod_path.split("/")
    mod_name = stripped_path[-1]

mod_info = open("mod_info.json", "r")
json_object = json.load(mod_info)
mod_info.close()

version = input("Enter new version number: ")
json_object["version"] = version


mod_info = open("mod_info.json", "w")
json.dump(json_object, mod_info, indent=4)
mod_info.close()

file_name = mod_name + ".zip"
with ZipFile(file_name, "w") as myzip:
    myzip.write(f"jars/{mod_name}.jar", arcname=f"DynamicTariffs/jars/{mod_name}.jar")
    myzip.write("mod_info.json", arcname=f"DynamicTariffs/mod_info.json")
    myzip.write("settings.json", arcname=f"DynamicTariffs/settings.json")