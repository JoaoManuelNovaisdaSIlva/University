import re


def purify(txt):
    tmap = txt.maketrans("ÇÁÀÃÂÉÈÊÍÌÎÓÒÔÕÚÙÛ","CAAAAEEEIIIOOOOUUU")
    pattern = re.compile('[^A-Z]+')
    return pattern.sub('', txt.upper().translate(tmap))
