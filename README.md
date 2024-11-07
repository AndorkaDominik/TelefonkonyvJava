# Telefonkönyv alkalmazás

Az applikáció célja, hogy egy egyszerűsített Telefonkönyv alkalmazást valósítson meg, amely lehetővé teszi a felhasználók számára a különböző személyes és üzleti kapcsolatok tárolását és kezelését. Az alkalmazás célja, hogy könnyen használható felületet biztosítson a kapcsolatok hozzáadására, módosítására, keresésére, és törlésére, valamint a kapcsolati adatok exportálására egy külső fájlba.

## Első lépések

- A projekt Java 11 nyelven készült.
- Az alkalmazás a [Maven](https://maven.apache.org/) eszközzel építhető.
- A tesztekhez a [JUnit](https://junit.org/junit4/) keretrendszer kerül felhasználásra, a függőségek izolálásához pedig a [Mockito](https://site.mockito.org/) könyvtár biztosít támogatást.

A projekt klónozása és fordítása:

```bash
mvn compile
```

A fordításhoz és a tesztek futtatásához az alábbi parancsot használd:


```bash
mvn test
```

A projekt code coverage tesztelése:

```bash
mvn clean test
```

Html eredmény fájl létrehozása:

```bash
mvn jacoco:report
```

## Áttekintés

A Telefonkönyv alkalmazás több funkciót kínál a felhasználók számára, beleértve az új kapcsolatok hozzáadását, a meglévő bejegyzések szerkesztését, törlését és különféle szűrési lehetőségeket a keresés során. Az alkalmazás grafikus felhasználói felületét a Swing könyvtár segítségével valósítjuk meg, a kapcsolatok megjelenítése pedig egy `JTable` komponensen keresztül történik.

### Fő funkciók

- **Kapcsolat hozzáadása**: Új személyes vagy üzleti kapcsolat felvitele a telefonkönyvbe, beleértve a nevet, telefonszámot, foglalkozást.
- **Kapcsolat módosítása**: A meglévő kapcsolatok adatainak szerkesztése és frissítése.
- **Kapcsolat törlése**: Kiválasztott kapcsolatok eltávolítása a telefonkönyvből.
- **Importálás fájlból**: Lehetőség van korábban elmentett kapcsolati adatok importálására külső fájlból, így a telefonkönyv gyorsan feltölthető meglévő adatokkal.
- **Exportálás fájlba**: A teljes telefonkönyv adatainak mentése egy külső fájlba, így később is elérhetőek lesznek az adatok.

### Technikai részletek

Az alkalmazás a Java kollekciós keretrendszerét használja a kapcsolatok tárolására és kezelésére, biztosítva ezzel a gyors és hatékony hozzáférést az adatokhoz.

A projektben a Maven szolgál a függőségek kezelésére és a build folyamat automatizálására, míg a JUnit és Mockito eszközök tesztelési keretrendszerként szolgálnak a funkciók helyességének ellenőrzésére.

### Használat

Az alkalmazás egy egyszerű, menüvezérelt felhasználói felületet kínál, amelyet kezdő és haladó felhasználók számára is könnyen áttekinthetővé teszünk. A felhasználói felületen megjelenő funkciók jól átlátható elrendezést biztosítanak, így a felhasználók könnyen navigálhatnak a különböző funkciók között.