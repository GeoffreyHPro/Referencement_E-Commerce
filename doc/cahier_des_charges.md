# Fonctionnalités par pages

## 1) Page de liste d'article

### Global
rechercher par (multiple possible) : categories (liste déroulante), nom d'article, reference d'article  
affichage en liste paginé (par 10, 50, 200) => forme du tableau : reference, nom, prix

### Client
afficher seulement les articles en stock et avec une durée de péremption > 5 jours  
Lors de l'achat, décrémenter le lot avec la dlc la plus proche

### Administrateur
navigation vers page modif d'article au clic sur l'article  
navigation vers page nouvel article via bouton "nouveau"



## 2) Page modification d'article

### Administrateur
formulaire pour modification d'article  
pour modification de stock, demander le lot concerné
  
  

## 3) Page nouvel article

### Administrateur
formulaire pour ajouter un article  



## 4) Page gestion des stocks (modif sur lot existant)

### Administrateur
voir les stocks des produits périssables  
voir les stocks des produits non périssables (trié par date de péremption)  
bouton navigation vers modification de l'article  



## 5) Page nouvel arrivage (creation d'un nouveau lot)

### Administrateur
#### ajouter un article :  
  numéro de lot  
  a reference de l'article  
  la quantité (met à jour la quantité d'un article)  
  la date limite de péremption



## 6) Page article périmé

### Administrateur
voir tous les lots d'article dont la date de consommation est dépassée  
pouvoir supprimer un lot



## 7) Page d'une commande

### Employé
lister les articles (le lot concerné) d'une commande par son numéro  
bouton pour remettre en stock un article  
  Si date de retrait de commande > 7 jours => refuser l'action  
  Sinon si périmé => ne pas ajouter au stock et juste le détruire  
  sinon remettre en stock  



## Option panier
Possibilité de valider sa commande





































































