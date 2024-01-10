# 2023_assignment3_BEerConnect

## Link Repo GitLab
https://gitlab.com/bicoccaprojects/2023_assignment3_beerconnect

## Partecipanti

- **Ficara Damiano - 919386**

- **Ricci Claudio - 918956**

## Descrizione dell'applicazione

L'applicazione vuole proporre un sistema di amministrazione per un network di amanti delle birre. L'applicazione è un backend Java basato su JPA che implementa le operazioni CRUD (Create, Read, Update, Delete) per tutte le entità del sistema.

L'obiettivo principale è fornire agli amanti delle birre un applicativo, capace di gestire in modo efficace tutte le attività legate alla loro passione. Grazie all'utilizzo di JPA nel nostro backend Java, garantiamo un'elevata efficienza nell'amministrazione del network, consentendo agli utenti di creare, leggere, aggiornare e eliminare facilmente le informazioni rilevanti.

### Diagramma ER

<div align="center">
  <img src="ER.png" alt="Image" width="700"/>
</div>

## Struttura progetto

### Entità

L'applicazione è strutturata attorno a diverse entità, ciascuna progettata per gestire specifici aspetti del network dedicato agli amanti delle birre. Di seguito sono elencate le entità implementate:

- Client: rappresenta un utente dell'applicativo, un appassionato delle birre che partecipa attivamente alla community. I campi che caratterizzano questa entità sono un nome, un indirizzo email (che deve essere univoco), una data di nascita (l'utente deve essere maggiorenne), un indirizzo e delle preferenze; 

- Pub: rappresenta un luogo di produzione di birre all'interno del network. I campi che caratterizzano questa entità sono un nome, la nazione di locazione, e un anno di fondazione;

- Beer: entità fondamentale che rappresenta una birra nel sistema. Caratterizzata da nome della birra (univoco per ognuna), tipo (Lager, Ale, Stout, ecc.), aroma, grado alcolico, colore, ingredienti, prezzo, quantità di birra prodotta e pub da cui è prodotta;

- Limited Edition: entità derivata da Beer, rappresenta un edizione limitata di un'altra birra, oltre ai campi dell'entità madre ha in aggiunta il nome della birra originale e l'anno di produzione.

Inoltre, ogni entità è caratterizzata da un campo identificativo (ID) che consente di riconoscere univocamente le varie istanze di ogni entità.

In ognuna di queste classi sono stati definiti gli attributi dell'entità e le annotazioni che permettono di gestire la persistenza dei dati sul db tramite JPA.

### Gestione dell'ereditarietà

La gestione delle entità Limited Edition richiede un approccio di mapping particolare, poiché i concetti di gerarchia presenti nella programmazione orientata agli oggetti non si traducono direttamente nei database. L'approccio scelto per affrontare questa sfida è il modello "Single Table", che prevede l'utilizzo di una singola tabella per gestire le entità Beer e Limited Edition, distinguendole attraverso l'uso di colonne specifiche.\
La tabella unica, denominata `beer` è progettata per incorporare tutti gli attributi comuni delle entità Beer e Limited Edition. La struttura della tabella comprende:

- Colonne per tutti gli attributi di Beer, che sono utilizzati anche da Limited Edition;

- Colonne aggiuntive specifiche di Limited Edition, che contengono valori NULL nelle righe corrispondenti a istanze di Beer;

- Una colonna `beer_type` di tipo stringa, che indica il tipo di oggetto rappresentato (Beer o Limited Edition). All'interno di questa colonna, per ogni istanza è contenuto il valore _normal_/_limited_ che indica se l'istanza appartiene alla classe Beer o Limited Edition.

Questo approccio "Single Table" è stato scelto perchè consente una gestione efficiente delle relazioni tra le entità Beer e Limited Edition, preservando al contempo la coerenza e la completezza dei dati nel contesto di un'unica tabella.

### Relazioni

L'applicazione presenta diverse relazioni tra le entità precedentemente descritte, delineando connessioni significative all'interno della comunità birraia. Di seguito sono illustrate le principali relazioni implementate:

- _Produzione_ tra Pub e Beer (one-to-many): un Pub può produrre molte birre, ma una birra è prodotta da un solo Pub. La connessione è gestita attraverso il campo `id_pub` presente in ciascuna entità di tipo Beer (e Limited Edition). Questo campo permette di associare ogni birra al pub specifico da cui è stata prodotta, garantendo così una chiara e univoca relazione di produzione.

- _Conosce_ (relazione self-loop su Client): questa relazione rappresenta la connessione tra i client che si conoscono all'interno della comunità birraia. Questa relazione è strutturata secondo lo schema follower/followed di Instagram, un Client può seguire _N_ altri utenti e venir seguito da _N_ utenti. Se il Client A segue il Client B, quest'ultimo non è obbligato a seguire anch'esso il Client A.\
La relazione è gestita attraverso la tabella `client_to_client` nel database, che contiene le colonne `client_id` e `id_client_followed`. Questa relazione offre la flessibilità in modo che un Client possa seguire più utenti senza necessariamente ricevere un follow in risposta.\
Lo scopo di questa relazione è permettere ai vari Client di andare a leggere le preferenze di gusto o le recensioni scritte dei Client che seguono o dei Client da cui vengono seguiti.

- _Recensione_ tra Client e Beer (many-to-many): ogni Client può scrivere _N_ recensioni su _N_ birre e ogni birra può ricevere _N_ recensioni da _N_ Client diversi.\
Questa relazione viene gestita da una tabella chiamata `client_review` che oltre a contenere le chiavi esterne della birra recensita e del Client che recensice, contiene due ulteriori campi, `rating` e `review`. Dove, il primo campo è un valore tra 1 e 5 che rappresenta un voto dato alla birra e il secondo contiene la descrizione verbale della recensione.\
La gestione avanzata di questa relazione richiede la creazione di un'entità intermedia ClientReview e l'uso di annotazioni JPA @OneToMany da entrambi i lati, garantendo così una gestione efficiente e completa delle recensioni birrarie. Nonostante questo la relazione rimane di tipo many-to-many.

### Implementazione

Per la gestione della persistenza, sono state introdotte interfacce all'interno del package repository, una per ogni entità, le quali estendono JpaRepository per sfruttare i metodi standard CRUD di JPA. Al fine di soddisfare i requisiti dell'assignment, sono state sviluppate query di ricerca specifiche coinvolgenti almeno due entità, le quali effettuano selezioni basate su attributi non chiave.

La funzione richiesta nelle specifiche del progetto, denominata `findClientByReview` e implementata nella classe `ClientReview`, consente di recuperare le informazioni relative ai clienti in base alle recensioni associate alle birre. In particolare, la funzione offre la possibilità di filtrare i risultati in base alla nazionalità della birra e al rating assegnato. 

Per la gestione della business logic, sono state implementate classi service all'interno del package service, ognuna dedicata a un'entità del sistema. Queste classi consentono l'implementazione dei metodi estesi dalle interfacce repository e l'utilizzo di metodi per le operazioni CRUD. Tale struttura offre un livello di controllo aggiuntivo tra l'applicazione e il database.

Le eccezioni sono gestite attraverso le classi apposite presenti nel package exception.

### Operazione di Search
In conformità con i requisiti, è stata implementata un'operazione di ricerca che coinvolge almeno due entità ed estrae entità eseguendo una selezione basata su parametri non chiave.

L'implementazione di questa operazione di ricerca è stata incorporata nella classe `Pub`. Essa utilizza una query strutturata per recuperare tutti i nomi dei pub di una determinata nazione (campo di `Pub`), i quali producono birre di un tipo specifico (campo dell'entità `Beer`) e hanno ricevuto recensioni con un rating superiore o uguale a un valore specifico (campo di `ClientReview`).

La query è possibile trovarla nell'interfaccia `PubRepository` e per verificare l'efficacia di questa operazione, è possibile eseguire test nell'apposita classe di test denominata `PubTests`.

## Come utilizzare l'applicazione

L'applicazione si appoggia su un database PostgreSQL, pertanto è necessario creare un database di questo tipo con il nome desiderato. Successivamente, definire il file `config.properties` (che deve essere posizionato in "2023_assignment3_beerconnect\src\main\resources"), e compilare i seguenti campi:

- spring.datasource.username="username_postgres"

- spring.datasource.password="password_postgres"

- spring.datasource.url=jdbc:postgresql://localhost:5432/"nome_db_scelto"

Assicurarsi di sostituire "username_postgres", "password_postgres", e "nome_db_scelto" con le credenziali appropriate e il nome scelto per il database.

## Testing

All'interno del progetto BeerConnect, l'implementazione dei test si configura come una pratica essenziale per garantire la solidità e la correttezza delle operazioni eseguite sulle diverse entità del sistema. Un focus particolare è stato dedicato alle classi di test per le entità chiave, evidenziando le operazioni di gestione delle birre, degli utenti, delle birre in edizione limitata e dei pub.

Le annotazioni `@BeforeEach` e `@AfterEach` sono utilizzate per preparare e ripulire l'ambiente di test prima e dopo ciascuna singola prova. La funzione `setUp` si occupa dell'inizializzazione mediante l'utilizzo di uno script SQL, mentre `tearDown` gestisce le operazioni di pulizia. Questo approccio mira a isolare i test, garantendo che ciascuno parta da uno stato noto e coerenza, contribuendo così alla ripetibilità e all'affidabilità.

La classe `BeerTests` è progettata per convalidare le operazioni CRUD relative alle birre. I test comprendono la ricerca e il recupero delle birre, l'aggiornamento delle informazioni e l'eliminazione delle birre. La correttezza di queste operazioni è fondamentale per assicurare che le birre nel sistema siano facilmente accessibili e che le modifiche avvengano in modo coerente.

Nei `ClientTests`, l'attenzione è stata riservata alla gestione degli utenti. I test abbracciano operazioni come l'aggiunta di nuovi utenti, la modifica delle informazioni e la gestione delle relazioni tra utenti, come il seguire e smettere di seguire altri utenti. Questi test sono cruciali per garantire che le funzionalità sociali di BeerConnect siano affidabili e che le informazioni degli utenti siano gestite correttamente.

Per quanto riguarda le birre in edizione limitata, la classe `LimitedEditionTests` si occupa di testare la corretta gestione di queste birre speciali. I test coprono la ricerca, l'aggiornamento e l'eliminazione delle birre in edizione limitata, assicurando che anche queste birre siano trattate in modo accurato e conforme alle aspettative del sistema.

Nei `PubTests` testano le operazioni relative ai pub all'interno di BeerConnect. Dai test di recupero e visualizzazione dei pub, all'aggiunta e modifica di informazioni, fino all'eliminazione di pub specifici, ogni aspetto viene attentamente valutato per garantire l'integrità dei dati relativi ai locali.

Infine, la classe `ClientReviewTests` è dedicata alla verifica delle operazioni fondamentali legate alle recensioni dei clienti in BeerConnect. I test coprono aspetti chiave come l'aggiunta, la modifica e la gestione delle recensioni, garantendo l'affidabilità delle funzionalità sociali dell'applicazione. Attraverso scenari diversificati, dai test di base come l'ottenimento di recensioni per ID alla gestione di situazioni più complesse come recensioni duplicate e aggiornamenti, si assicura la corretta gestione delle informazioni dei clienti all'interno del sistema.
