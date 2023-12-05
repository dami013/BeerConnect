# 2023_assignment3_BEerConnect


Entità:
    Persona:
        Attributi: ID, Nome, Email, Data di Nascita
    Cliente (sottoclasse di Persona):
        Attributi Aggiuntivi: Codice Cliente, Indirizzo di Spedizione, Preferenze di Gusto
    Birrificio:
        Attributi: ID Birrificio, Nome, Località, Anno di Fondazione
    Birra:
        Attributi: ID Birra, Nome, Tipo (Lager, Ale, Stout, ecc.), Grado Alcolico, IBU (Unità Bitterness)
    Edizione Limitata:
        Attributi: ID Edizione, Nome, Anno di Produzione, Quantità Limitata

Relazioni:
    Produzione (tra Birrificio e Birra):
        Un Birrificio può produrre molte Birre, ma una Birra è prodotta da un solo Birrificio (one-to-many).
    Assaggio (tra Cliente e Birra):
        Un Cliente può assaporare molte Birre, e una Birra può essere assaporata da molti Clienti (many-to-many).
    Conosce (relazione self-loop su Persona):
        Una Persona può conoscere altre Persone. Questa relazione rappresenta la connessione tra i clienti o degustatori esperti che si conoscono all'interno della comunità birraia.
    Recensione (tra Cliente e Birra):
        Un Cliente può scrivere molte Recensioni, ma una Recensione è associata a un solo Cliente e a una sola Birra (many-to-one).
    Edizione Speciale (tra Birra e Edizione Limitata):
        Una Birra può avere una sola Edizione Limitata, e un'Edizione Limitata è associata a una sola Birra (one-to-one).

Questo contesto consente agli utenti di esplorare una vasta gamma di birre prodotte dai birrifici, assaporare le birre, condividere opinioni attraverso recensioni e conoscere altri appassionati di birra all'interno di una comunità. L'aggiunta dell'entità "Edizione Limitata" consente di gestire particolari versioni speciali e limitate di birre.
