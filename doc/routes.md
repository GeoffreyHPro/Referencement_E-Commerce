# ROUTES 


    /
    
    Global  -- GET       affiche le formulaire de connexion | si connexion -> /Articles sinon -> / 

    -------------------------------------------------------
    /Articles

    Global  -- GET       affiche tous les produits

    /Articles?price=50&page=20
    /Articles?category=mobilier&price=60
    
    Global  -- GET       affiche les produits avec des critères 


    /Articles/{id}

    Client  -- GET       affiche les details du produit
    Admin   -- GET       affiche une page pour modifier le produit (remettre des stocks ...)
    Employe -- GET       affiche une page pour modifier le produit

    Admin   -- PUT       fait la modification du produit      
    Employe -- PUT       fait la modification du produit  


    /Articles/add

    Admin   -- GET       Formulaire permettant l'ajout d'un produit ou plusieurs (lot)
    Admin   -- POST      ajoute le produit a la base de données | si ajout -> /add


    /Articles/perished

    Admin   -- GET       Affiche tous les articles périmés et peut effectuer des actions dessus


    ------------------------------------------------------------

    /Panier

    Client  -- GET       affiche les articles dans le panier(permet la validation des articles)