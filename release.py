from zipfile import ZipFile

file_name="Dynamic Tariffs.zip"

with ZipFile(file_name, "w") as myzip:
    myzip.write("jars/Dynamic_Tariffs.jar")
    myzip.write("mod_info.json")
    myzip.write("settings.json")

