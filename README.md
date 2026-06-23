# TotemApp - Sistema di Gestione Ordini Versatile

TotemApp è un'applicazione Android nativa progettata per la gestione di ordini e vendite in contesti di vendita diretta o self-service. L'architettura è stata pensata per essere **agnostica rispetto al prodotto**: l'app può essere configurata facilmente per gestire un catalogo di alimentari, elettronica, abbigliamento o qualsiasi altro tipo di merce.

Il progetto è stato sviluppato come parte di un percorso PCTO presso l'azienda Soltec.

## 🚀 Funzionalità Principali
- **Catalogo Dinamico:** Gestione di prodotti suddivisi per categorie, facilmente estensibile tramite database locale.
- **Supporto Multilingua:** Sistema di traduzione dinamica per nomi e descrizioni dei prodotti.
- **Gestione Ordini:** Carrello persistente basato su pattern Singleton per una navigazione fluida tra le schermate.
- **User Management:** Sistema di autenticazione con salvataggio dati su database SQLite locale.
- **Esperienza Utente:** Dark Mode integrata per adattarsi a diverse condizioni di luce e design moderno basato su Material Design.

## 🛠 Architettura del Progetto
Il progetto segue i principi del pattern **MVC (Model-View-Controller)**, garantendo una separazione netta tra logica e interfaccia:
- **Model:** `DatabaseHelper.java` gestisce l'interazione con SQLite. Le entità (POJO) come `Prodotto.java` definiscono la struttura dei dati.
- **View:** Interfacce XML ottimizzate con `ConstraintLayout` per garantire responsività su diversi formati di schermo.
- **Controller:** `Activity` e `Adapter` gestiscono la logica di navigazione, il binding dei dati e l'interazione con l'utente.

## ⚙️ Tecnologie Utilizzate
- **Linguaggio:** Java
- **Database:** SQLite (tramite `SQLiteOpenHelper`)
- **Persistence:** SharedPreferences (per preferenze utente e configurazioni tema)
- **Design:** Material Design, ConstraintLayout, RecyclerView

## 🏗 Setup e Installazione
1. Clona il repository: `git clone https://github.com/sleepydoggo-dev/PROGETTOTOTEM.git`
2. Apri il progetto con **Android Studio**.
3. Sincronizza il progetto tramite il file `build.gradle`.
4. Esegui l'app su un emulatore o dispositivo fisico con Android API 24+.

## 📝 TODO & Sviluppi Futuri
- [ ] Implementazione di un sistema di storico ordini avanzato.
- [ ] Integrazione di API per pagamenti elettronici (Gateway di pagamento).
- [ ] Ottimizzazione della persistenza dati tramite architettura MVVM.

---
*Progetto sviluppato da Alessandro (Ale) durante il PCTO presso Soltec - 2026*
