P = 99494096650139337106186933977618513974146274831566768179581759037259788798151499814653951492724365471316253651463342255785311748602922458795201382445323499931625451272600173180136123245441204133515800495917242011863558721723303661523372572477211620144038809673692512025566673746993593384600667047373692203583
G = 44157404837960328768872680677686802650999163226766694797650810379076416463147265401084491113667624054557335394761604876882446924929840681990106974314935015501571333024773172440352475358750668213444607353872754650805031912866692119819377041901642732455911509867728218394542745330014071040326856846990119719675

def main():
    aliceSecret = 6  # a
    bobSecret = 15  # b

    # Begin
    print("Publicly Shared Variables:")
    print("    Publicly Shared Prime: ", P)
    print("    Publicly Shared Base:  ", G)

    # Alice Sends Bob A = g^a mod p
    A = (G ** aliceSecret) % P
    print("\n  Alice Sends Over Public Chanel: ", A)

    # Bob Sends Alice B = g^b mod p
    B = (G ** bobSecret) % P
    print( "Bob Sends Over Public Chanel: ", B )

    print("\n------------\n")
    print("Privately Calculated Shared Secret:")
    # Alice Computes Shared Secret: s = B^a mod p
    aliceSharedSecret = (B ** aliceSecret) % P
    print("    Alice Shared Secret: ", aliceSharedSecret)

    # Bob Computes Shared Secret: s = A^b mod p
    bobSharedSecret = (A ** bobSecret) % P
    print("    Bob Shared Secret: ", bobSharedSecret)

main()