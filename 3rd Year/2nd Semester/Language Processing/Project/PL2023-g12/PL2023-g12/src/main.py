import json
from lex import lexer
from yacc import yacc
import os, sys

 
def scanDirectory(path):
    obj = os.scandir(path)
    for entry in obj :
        if entry.is_dir():
            scanDirectory(path + "/" + entry.name)
        if entry.is_file():
            extension = os.path.splitext(entry.name)[1]
            if extension == ".toml":
                file_final = entry.name[:-4] + "json"
                print(file_final)
                print(path)
                file = open(path + "/" + entry.name)

                inp = file.read()

                lexer.input(inp)
                parsed_dict = yacc.parse(inp, lexer=lexer)
                json_str = json.dumps(parsed_dict, indent=2)
                print(json_str)
                file.close()

                with open( path + "/" + file_final, 'w') as outfile:
                    json.dump(parsed_dict, outfile)
                #print(entry.name, extension)



def main():
    args = sys.argv[1:]
    #args = ["--file", "example1.toml"]

    if len(args) != 2:
        print("Please use ./main.py --dir <dir-to-scan> or ./main.py --file <file-to-scan>")
    else:
        if args[0] == "--dir":
            scanDirectory(args[1])
        if args[0] == "--file":
            filename = args[1]
            file = open(filename)

            inp = file.read()

            lexer.input(inp)
            parsed_dict = yacc.parse(inp, lexer=lexer)
            json_str = json.dumps(parsed_dict, indent=2)
            print(json_str)
            file.close()

            with open( filename[:-4] + "json" , 'w') as outfile:
                json.dump(parsed_dict, outfile)


if __name__ == '__main__':
    main()
