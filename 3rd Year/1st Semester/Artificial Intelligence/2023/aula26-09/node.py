
class Nodo:

    def __init__(self,n):
        self.m_name = n


    def __str__(self):
        return "node " + self.m_name

    def __repr__(self):
        return "node " + self.m_name

    def getName(self):
        return self.m_name

    def setName(self, name):
        self.m_name = name


    def __eq__(self, other):
        if isinstance(other, type(self)):
            return self.m_name == other.m_name
        else:
            return False

    def __hash__(self):
        return hash(self.m_name)
