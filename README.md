# Calculator for APS class 
Uses the Reverse polish notation and calculates the result of input in arguments, can use 42 Stacks to perform operations and use functions, loops, etc.

# Building and running
Build the class `Naloga1.java` with javac, then run with program `java Naloga1` and with every line write a program that you want it to execute.
Made with the original exercise requirements, note the formatting was not perfect (in Slovene): <br>
Kalkulator naj podpira naslednje ukaze oz. operacije (nad glavnim skladom):

    echo - v vrstici izpiše vrh sklada 0 (sklad pusti nespremenjen); če je sklad prazen, izpiše prazno vrstico
    pop - odstrani vrh sklada 0
    dup - podvoji vrh sklada 0 (x -> x x)
    dup2 - podvoji par na vrhu sklada 0 (x y -> x y x y)
    swap - zamenja vrhnja dva elementa sklada 0 (x y -> y x)

Naslednje operacije zamenjajo vrh glavnega  sklada z ustreznim rezultatom (x -> y):

    char - vrh sklada 0 zamenja z znakom, ki ima ASCII/Unicode kodo vrha sklada
    even - vrh sklada 0 zamenja z 1, če je vrh sod, sicer z 0
    odd - vrh sklada 0 zamenja z 1, če je vrh lih, sicer z 0
    ! - vrh sklada 0 zamenja s faktorielo vrha
    len - vrh sklada 0 zamenja z dolžino elementa na vrhu

Naslednje operacije zamenjajo vrhnja dva elementa glavnega sklada z ustreznim rezultatom (x y -> r):

    <> - primerja zgornja dva elementa (x y) sklada 0 in na sklad porine 1 (če x <> y) ali 0 (če x == y)
    < - primerja zgornja dva elementa sklada 0 in na sklad porine 1 (če x < y) ali 0 (sicer)
    <= - primerja zgornja dva elementa sklada 0 in na sklad porine 1 (če x <= y) ali 0 (sicer)
    == - primerja zgornja dva elementa sklada 0 in na sklad porine 1 (če x == y) ali 0 (sicer)
    > - primerja zgornja dva elementa sklada 0 in na sklad porine 1 (če x > y) ali 0 (sicer)
    >= - primerja zgornja dva elementa sklada 0 in na sklad porine 1 (če x >= y) ali 0 (sicer)
    + - na sklad 0 porine vsoto vrhnjih dveh elementov sklada
    - - na sklad 0 porine razliko vrhnjih dveh elementov sklada
    * - na sklad 0 porine zmnožek vrhnjih dveh elementov sklada
    / - na sklad 0 porine kvocient (celoštevilsko deljenje) vrhnjih dveh elementov sklada
    % - na sklad 0 porine ostanek po deljenju elementa pod vrhom z elementom na vrh
    . - stakne (združi, zlepi) vrhnja dva elementa sklada 0 v en element (x y -> xy)
    rnd - na sklad 0 porine naključno število, ki ima vrednost >= x in <= y 

Naslednje operacije omogočajo izvedbo pogojnega stavka (izpolnjenost pogoja hranimo v interni spremeljivki):

    then  – z glavnega sklada 0 vzame vrhnje število; če je to različno od 0, nastavi izpolnjenost pogoja na true, sicer pa na false

    else – zanika izpolnjenost pogoja
    ?... – vsak ukaz, ki se začne z ?, se izpolni (ali pa ne) glede na prednastavljeno izpolnjenost pogoja

Za delo s poljubnim skladom (glavnim ali pomožnimi) imamo na voljo spodnje ukaze. Pri tem velja, da število na vrhu glavnega sklada 0 določa indeks sklada, nad katerim se izvaja ukaz:

    print - v vrstici izpiše vsebino sklada (z indeksom, ki je podan na vrhu glavnega sklada 0) od dna do vrha (sklad ostane nespremenjen)
    clear – izprazne sklad (z indeksom, ki je podan na vrhu glavnega sklada 0)
    run – izvede vse ukaze na (pomožnem) skladu (z indeksom, ki je podan na vrhu glavnega sklada 0) od dna do vrha (sklad ostane nespremenjen)
    loop - izvede vse ukaze na (pomožnem) skladu (z indeksom, ki je podan na vrhu glavnega sklada 0) od dna do vrha (sklad ostane nespremenjen), pri čemer to ponovi tolikokrat, kot je podano s  številom pod vrhom sklada 0
    fun – na pomožni sklad  (z indeksom, ki je podan na vrhu glavnega sklada 0) zapiše toliko naslednjih ukazov, kolikor določa število pod vrhom glavnega sklada 0 
    move – z glavnega sklada prenese na pomožni sklad  (z indeksom, ki je podan na vrhu glavnega sklada 0) toliko elementov, kolikor določa število pod vrhom glavnega sklada 0 (elementi se prenesejo eden za drugim)
    reverse - obrne vrstni red vseh elementov na skladu  (z indeksom, ki je podan na vrhu glavnega sklada 0) - u v x y z -> z y x v u

Če ukaz ni na seznamu zgoraj naštetih, potem gre za element (število ali niz), ki se porine na vrh glavnega sklada 0 (push).

# Examples
The > means the following line is the input of the program.
 \> 0 -1 3 5 -7 11 -13 17 dup2 echo pop echo swap 0 print 17 -13 <br> 
0 -1 3 5 -7 11 -13 -13 17 

\> 151 131 + echo -100 140 - echo + echo <br>

282
-240
42

\> 3 5 11 17 0 print + + 10 * 0 print * 11 / echo <br>

3 5 11 17
3 330
90

\> 6 ! echo 42 == echo even 0 print <br>
720
0
1

\> 65 90 rnd echo char echo <br>
<br> 66
B

\> 0 1 2 3 4 3 4 4 1 fun dup 0 reverse swap 2 2 move 0 print 1 print 2 print 0 1 2 3 4 dup 0 reverse swap <br>
<br> 4 3

\> 0 1 2 3 3 1 fun 0 reverse dup 0 print 1 run 0 print 2 1 loop 0 print <br>
<br> 0 1 2 3 
3 2 1 0 0  
33 2 1 0 0 0

\> 7 3 1 2 5 1 fun == then ?dup2 else ?+ 1 run 0 print
<br>10

\> 9 1 fun dup 0 reverse swap % dup then ?1 ?run 24 10 0 print 1 run pop echo
<br>24 10
2

\> 3 1 fun 0 100 rnd 3 2 fun 5 1 loop 7 3 fun dup2 <= then ?pop else ?swap ?pop 3 4 fun 4 3 loop 1 print 2 print 3 print 4 print 2 run 0 print 4 run 0 print 0 100 rnd 1 5 loop dup2 <= then ?pop else ?swap ?pop 4 3 loop 
<br> 34 96 12 48 24
12
