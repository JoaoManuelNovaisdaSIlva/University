import csv
import json

def read_csv_file(file_path):
    bd=[]
    try:
        with open(file_path, "r") as csv_file:
            csv_reader = csv.DictReader(csv_file, delimiter=';')

            for row in csv_reader:
                bd.append(row)

    except FileNotFoundError:
        print("Erro ao abrir o ficheiro")
    except Exception as e:
        print(f"Erro: {e}")

    return bd

def pretence(valor, dicList):
    encotrado=False
    i=0
    while i<len(dicList) and not 

def calc_especies(bd):
    especies=[]
    counter=1
    for reg in bd:
        if(reg['BreedIDDesc'] != "") and not pertence(reg['BreedIDDesc'], especies):
            especies.append({
                'id': 'a'+str(counter),
                'designacao': reg['BreedIDDesc']
            })

    return especies

def calc_animais(bd):
    especies=[]
    counter=1
    for reg in bd:
        if(reg['BreedIDDesc'] != "") and not pertence(reg['BreedIDDesc'], especies):
            especies.append({
                'id': 'a'+str(counter),
                'designacao': reg['BreedIDDesc']
            })

    return especies

myBD = read_csv_file("Health_AnimalBites.csv")
especies = calc_especies(myBD)
animais = calc_animais(myBD)