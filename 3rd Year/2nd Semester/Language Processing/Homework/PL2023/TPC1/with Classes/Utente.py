
class Utente:

    def __init__(self, idade, sexo, tensao, colestrol, batimento, temDoenca):
        self.idade = idade
        self.sexo = sexo
        self.tensao = tensao
        self.colestrol = colestrol
        self.batimento = batimento
        self.temDoenca = temDoenca

    def getIdade(self):
        return self.idade

    def getSexo(self):
        return self.sexo

    def getTensao(self):
        return self.tensao

    def getColestrol(self):
        return self.colestrol

    def getBatimento(self):
        return self.batimento

    def getTemDoenca(self):
        return self.temDoenca

    def setIdade(self, idade):
        self.idade = idade

    def setSexo(self, sexo):
        self.sexo = sexo

    def setTensao(self, tensao):
        self.tensao = tensao

    def setColestrol(self, colestrol):
        self.colestrol = colestrol

    def setBatimento(self, batimento):
        self.batimento = batimento

    def setTemDoenca(self, temDoenca):
        self.temDoenca = temDoenca
