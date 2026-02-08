## Mitä tarkoittaa navigointi Jetpack Composessa

Navigointi Jetpack Composessa tarkoittaa siirtymistä eri composable-ruutujen välillä sovelluksen sisällä. Ruutuja ei vaihdeta Activityjen avulla, vaan navigointikirjasto hallitsee sitä, mikä composable näkyy näytöllä. Siirtyminen tapahtuu reittien (route) perusteella.

## Mitä ovat NavHost ja NavController

NavController on olio, joka ohjaa navigointia. Sen avulla siirrytään ruudulta toiselle kutsumalla `navigate(reitti)`.

NavHost määrittelee sovelluksen navigaatiokaavion. Siinä kerrotaan:
- mikä on aloitusruutu
- mitä reittejä sovelluksessa on
- mikä composable-funktio vastaa mitäkin reittiä

NavHost käyttää NavControlleria navigoinnin toteuttamiseen.

## Miten sovelluksen navigaatiorakenne on toteutettu (Home ↔ Calendar)

Sovelluksessa on määritelty reitit Home- ja Calendar-ruuduille. MainActivityssä luodaan NavController ja NavHost, jossa aloitusruutuna on Home.

HomeScreenistä siirrytään CalendarScreeniin kutsumalla:
`navController.navigate(ROUTE_CALENDAR)`

CalendarScreenistä palataan HomeScreeniin kutsumalla:
`navController.navigate(ROUTE_HOME)`

Navigointi on siis toteutettu reittien avulla, ja NavController huolehtii ruudun vaihtamisesta.

<br>




## Miten MVVM ja navigointi yhdistyvät (yksi ViewModel kahdelle screenille)

Sovelluksessa käytetään MVVM-arkkitehtuuria, jossa ViewModel vastaa sovelluksen datasta ja logiikasta, ja Composable-ruudut näyttävät käyttöliittymän.

MainActivityssä luodaan yksi TaskViewModel, joka annetaan parametrina sekä HomeScreenille että CalendarScreenille. Näin molemmat ruudut käyttävät samaa ViewModel-instanssia, vaikka navigointi vaihtaa näkyvää ruutua.

Navigointi siis vaihtaa näkymää, mutta ViewModel säilyy samana.

## Miten ViewModelin tila jaetaan kummankin ruudun välillä

TaskViewModel sisältää tilan StateFlow-muodossa, esimerkiksi tehtävälistan. Molemmat ruudut lukevat tämän tilan `collectAsState()`-funktion avulla.

Koska molemmat ruudut käyttävät samaa ViewModelia, ne näkevät saman datan. Kun tehtävä lisätään, muokataan tai poistetaan toisessa ruudussa, muutos päivittyy automaattisesti myös toisessa ruudussa.

Tila on siis keskitetty ViewModeliin, ja ruudut vain reagoivat siihen.

<br>




## Miten CalendarScreen on toteutettu

CalendarScreen hakee tehtävälistan ViewModelista `collectAsState()`-funktion avulla.  

Tehtävät ryhmitellään eräpäivän perusteella käyttämällä `groupBy`-funktiota. Tämä tarkoittaa, että kaikki saman päivämäärän tehtävät laitetaan samaan ryhmään.

LazyColumnissa käydään ensin läpi jokainen päivämäärä:
- Näytetään päivämäärä otsikkona
- Sen alle listataan kyseisen päivän tehtävät korteiksi

Tällä tavalla näkymä muistuttaa kalenteria, koska tehtävät on jaettu päivittäin.

## Miten AlertDialog hoitaa addTask ja editTask

AddDialogissa käyttäjä syöttää uuden tehtävän tiedot TextField-kenttiin. Kun painetaan Save, luodaan uusi Task-olio ja kutsutaan `onSave`, joka välittää tehtävän ViewModelille lisättäväksi listaan.

EditDialogissa näytetään valitun tehtävän nykyiset tiedot. Käyttäjä voi muokata niitä ja painaa Save, jolloin kutsutaan `onUpdate`, joka päivittää tehtävän ViewModelissa.  

EditDialogissa on myös delete-toiminto, joka kutsuu `onDelete`, ja ViewModel poistaa tehtävän listasta.

Dialogit eivät itse muuta listaa suoraan, vaan ne kutsuvat ViewModelin funktioita. ViewModel päivittää tilan, ja käyttöliittymä päivittyy automaattisesti.

