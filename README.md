# 2023_assignment3_BEerConnect

## Link Repo GitLab
https://gitlab.com/bicoccaprojects/2023_assignment3_beerconnect

## Partecipanti

- **Ficara Damiano - 919386**

- **Ricci Claudio - 918956**

## Descrizione dell'applicazione

L'applicazione vuole proporre un sistema di amministrazione per un network di amanti delle birre. L'applicazione è un backend Java basato su JPA che implementa le operazioni CRUD (Create, Read, Update, Delete) per tutte le entità del sistema.

L'obiettivo principale è fornire agli amanti delle birre un applicativo, capace di gestire in modo efficace tutte le attività legate alla loro passione. Grazie all'utilizzo di JPA nel nostro backend Java, garantiamo un'elevata efficienza nell'amministrazione del network, consentendo agli utenti di creare, leggere, aggiornare e eliminare facilmente le informazioni rilevanti.

## Struttura progetto

### Entità

L'applicazione è strutturata attorno a diverse entità, ciascuna progettata per gestire specifici aspetti del network dedicato agli amanti delle birre. Di seguito sono elencate le entità implementate:

- Client: rappresenta un utente dell'applicativo, un appassionato delle birre che partecipa attivamente alla community. I campi che caratterizzano questa entità sono un nome, un indirizzo email (che deve essere univoco), una data di nascita (l'utente deve essere maggiorenne), un indirizzo e delle preferenze; 

- Pub: rappresenta un luogo di produzione di birre all'interno del network. I campi che caratterizzano questa entità sono un nome, la nazione di locazione, e un anno di fondazione;

- Beer: entità fondamentale che rappresenta una birra nel sistema. Caratterizzata da nome della birra (univoco per ognuna), tipo (Lager, Ale, Stout, ecc.), aroma, grado alcolico, colore, ingredienti, prezzo, quantità di birra prodotta e pub da cui è prodotta;

- Limited Edition: entità derivata da Beer, rappresenta un edizione limitata di un'altra birra, oltre ai campi dell'entità madre ha in aggiunta il nome della birra originale e l'anno di produzione.

Inoltre, ogni entità è caratterizzata da un campo identificativo (ID) che consente di riconoscere univocamente le varie istanze di ogni entità.

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

### Diagramma ER

<div align="center">
  <img src="ER.png" alt="Image" width="700"/>
</div>

### Operazioni CRUD per ogni entità

###  search operation
that involves a minimum of two entities and extracts entities making a selection according to a constraint defined on non-key attributes

### TEST?
