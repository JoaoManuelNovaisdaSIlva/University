

class Node:
    def __init__(self, tipo, id, x, y):
        # tipo pode ser Parede, Inicio, Fim, Caminho, em maiusculas
        # x e y são as coordenadas para cada nodo
        self.m_id = id
        self.m_type = str(tipo)
        self.m_positionX = x
        self.m_positionY = y

    def __str__(self):
        return "nodo: " + str(self.m_id) + " do tipo: " + self.m_type + " nas coordenadas (x,y): " + "(" + str(self.m_positionX) + "," + str(self.m_positionY) + ")"

    def __repr__(self):
        return "nodo: " + str(self.m_id) + " do tipo: " + self.m_type + " nas coordenadas (x,y): " + "(" + str(self.m_positionX) + "," + str(self.m_positionY) + ")"

    def getTipo(self):
        return self.m_type

    def getId(self):
        return self.m_id

    def getX(self):
        return self.m_positionX

    def getY(self):
        return self.m_positionY

    def setTipo(self, tipo):
        self.m_type = str(tipo)

    def setId(self, id):
        self.m_id = id

    def setX(self, x):
        self.m_positionX = x

    def setY(self,y):
        self.m_positionY

    def __eq__(self, other):
        if isinstance(other, type(self)):
            return self.m_id == other.m_id
        else:
            return False

    def __hash__(self):
        return hash(self.m_id)
