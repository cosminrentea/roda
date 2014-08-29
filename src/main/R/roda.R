
# Ex. 1: o variabila categoriala v1 cu 105 de observatii, dintre care 5 de missing

# mylist <- list(vars = list(v1 = c(2,2,4,3,4,99,1,2,2,1,2,2,3,1,3,99,4,1,4,4,2,4,3,1,3,97,4,1,2,1,4,1,4,99,3,1,4,3,4,4,3,3,4,3,4,2,4,3,4,3,2,2,1,2,2,3,4,4,1,4,2,1,99,4,3,2,4,2,4,3,4,2,3,2,3,3,4,4,3,2,4,4,2,4,4,4,4,3,2,4,2,3,1,2,3,2,3,4,3,2,3,2,4,4,3)),
#                meta = list(v1 = c("Foarte putin"=1, "Putin"=2, "Mult"=3, "Foarte mult"=4, "Nu e cazul"=97, "Nu raspund"=99)))


# Ex. 2: o singura variabila numerica cu 135 observatii

# mylist <- list(vars=list(v1 = sample(1:100, 135, replace=T)), 
#                meta=list(v1=c()))


# Ex. 3: o singura variabila ordinala pe o scala de raspuns 1...7, care poate fi interpretata si numeric

# mylist <- list(vars = list(v1 = c(97, 99, sample(1:7, 122, replace=T), 99)),
#                meta = list(v1 = c("Foarte putin"=1, "Foarte mult"=7, "Nu e cazul"=97, "Nu stiu"=98, "Nu raspund"=99)))


getStats <- function(mylist) {
    
    # aceasta comanda poate sta aici (caz in care se incarca la fiecare apelare din Java
    # sau poate fi specificata doar la deschiderea R, in fisierul .Rprofile din /home/user
    load("miss.Rdata")
    
    
    rs <- function(x) {
        paste(rep(" ", x), collapse="")
    }
    
    
    ord1 <- function(mylist) {
        valori <- as.vector(mylist$meta[[1]])
        etichete <- names(mylist$meta[[1]])
        frecventa <- as.vector(table(factor(mylist$vars[[1]], levels=valori, labels=etichete)))
        
        json <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Tabel de frecvente pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(valori) + 1, ",\n",
                            rs(12), "\"cols\": ", 3, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"Nr.\", \"Categorie\", \"Frecventa\"],\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "[\"", valori[i],
                                                  "\", \"", etichete[i],
                                                  "\", \"", frecventa[i],
                                                  ifelse(i == length(valori), "\"]\n", "\"],\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Diagrama bara pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"charttype\": \"bar\",\n",
                            rs(12), "\"height\": ", 85*length(valori), ",\n",
                            rs(12), "\"data\": [\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "{\n",
                                                rs(20), "\"name\": \"", etichete[i], "\",\n",
                                                rs(20), "\"value\": ", frecventa[i], "\n",
                                            rs(16), ifelse(i == length(valori), "}\n", "},\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
        return(json)
    }
    
    
    num1 <- function(mylist) {
        
        # se elimina valorile de missing (daca exista)
        if (length(mylist$meta[[1]]) > 0) {
            ismiss <- toupper(names(mylist$meta[[1]])) %in% miss
            if (any(ismiss)) {
                mylist$vars[[1]] <- mylist$vars[[1]][!mylist$vars[[1]] %in% mylist$meta[[1]][ismiss]]
            }
        }
        
        numerice <- summary(mylist$vars[[1]])
        
        json <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Masuri numerice pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(numerice) + 1, ",\n",
                            rs(12), "\"cols\": ", 2, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"\", \"", names(mylist$vars)[1], "\"],\n", sep="")
                                for (i in seq(length(numerice))) {
                                    json <- paste(json, rs(16), "[\"", names(numerice)[i],
                                                  "\", \"", numerice[i],
                                                  ifelse(i == length(numerice), "\"]\n", "\"],\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
        return(json)
    }
    
    
    numord1 <- function(mylist) {
        
        valori <- etichete <- sort(unique(mylist$vars[[1]]))
        
        myvar <- mylist$meta[[1]]
        
        if (length(mylist$meta[[1]]) > 0) {
            
            # se elimina valorile de missing (daca exista)
            ismiss <- toupper(names(mylist$meta[[1]])) %in% miss
            
            if (any(ismiss)) {
                
                myvar <- mylist$vars[[1]][!mylist$vars[[1]] %in% mylist$meta[[1]][ismiss]]
                
            }
            
            # se inlocuiesc celelalte etichete in afara de missing
            for (i in seq(length(mylist$meta[[1]]))) {
                
                pozitie <- match(mylist$meta[[1]][i], valori)
                
                if (!is.na(pozitie)) {
                    
                    etichete[pozitie] <- names(mylist$meta[[1]])[i]
                    
                }
                
            }
            
            # se adauga (daca exista) alte etichete pentru valori care nu exista in date
            diferente <- setdiff(mylist$meta[[1]], valori)
            
            if (length(diferente) > 0) {
                
                valori <- c(valori, diferente)
                
                etichete <- c(etichete, names(mylist$meta[[1]])[mylist$meta[[1]] %in% diferente])
                
                etichete <- etichete[order(valori)]
                
                valori <- sort(valori)
                
            }
            
        }
        
        numerice <- summary(myvar)
        
        print(valori)
        print(etichete)
        
        frecventa <- as.vector(table(factor(mylist$vars[[1]], levels=valori, labels=etichete)))
        
        json <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Masuri numerice pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(numerice) + 1, ",\n",
                            rs(12), "\"cols\": ", 2, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"\", \"", names(mylist$vars)[1], "\"],\n", sep="")
                                for (i in seq(length(numerice))) {
                                    json <- paste(json, rs(16), "[\"", names(numerice)[i],
                                                  "\", \"", numerice[i],
                                                  ifelse(i == length(numerice), "\"]\n", "\"],\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Tabel de frecvente pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(valori) + 1, ",\n",
                            rs(12), "\"cols\": ", 3, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"Nr.\", \"Categorie\", \"Frecventa\"],\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "[\"", valori[i],
                                                  "\", \"", etichete[i],
                                                  "\", \"", frecventa[i],
                                                  ifelse(i == length(valori), "\"]\n", "\"],\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Diagrama bara pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"charttype\": \"bar\",\n",
                            rs(12), "\"height\": ", 85*length(valori), ",\n",
                            rs(12), "\"data\": [\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "{\n",
                                                rs(20), "\"name\": \"", etichete[i], "\",\n",
                                                rs(20), "\"value\": ", frecventa[i], "\n",
                                            rs(16), ifelse(i == length(valori), "}\n", "},\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
        return(json)
    }
    
    
    checkVar <- function(mylist) {
        
        # toata aceasta functie va fi rescrisa daca tipul variabilei este primit de la server
        # deocamdata, trebuie dedus daca este categoriala sau numerica (sau ambele)
        
        if (length(mylist$meta[[1]]) > 0) {
            
            # posibil variabila categoriala
            # insa si variabilele numerice pot avea etichete de valori (de missing)
            
            # verific daca numarul de valori unice din date este (cel mult) egal cu numarul de categorii din metadate
            valunice <- unique(mylist$vars[[1]])
            
            if (length(valunice) <= length(mylist$meta[[1]])) {
                
                # cu siguranta este o variabila categoriala
                
                return("categoriala")
                
            }
            else {
                
                # numarul unic de valori este mai mare decat numarul de categorii din metadate
                # posibil sa fie o variabila numerica de tip 1...10 cu doar doua etichete (la 1 in stanga si la 10 in dreapta)
                # sau poate fi o variabila categoriala pentru care a uitat cineva sa eticheteze toate valorile
                
                
                # valorile unice care nu au etichete
                valfaraet <- valunice[! valunice %in% mylist$meta[[1]]]
                
                
                # 5 si 8 sunt numere arbitrare,
                # ma gandesc la cea mai mica scala "numerica" cu etichete, de tip 1...7
                # si la cea mai mare scala "numerica" cu etichete, de tip 1...10
                
                if (length(valfaraet) < 5) {
                    
                    return("ordinala")
                    
                }
                else if (length(valfaraet) < 9) {
                    
                    return("numord")
                    
                }
                else {
                    
                    return("numerica")
                    
                }
                
            }
            
        }
        else {
            
            # e o variabila numerica, codul nu ar trebui sa ajunga aici decat foarte rar
            # intotdeauna ar trebui sa existe valori de missing undeva in metadate, chiar si pentru variabile numerice
            
            return("numerica")
            
        }
        
    }
    
    
    
    if (length(mylist$vars) == 1) { # o singura variabila primita de la server
        
        vartype <- checkVar(mylist)
        
        if (vartype == "categoriala") {
            
            return(ord1(mylist))
            
        }
        else if (vartype == "numord") {
            
            return(numord1(mylist))
            
        }
        else { # vartype == "numerica"
            
            return(num1(mylist))
            
        }
    }
    else { # nu mai mult de 2 variabile
        
        tip <- rep("", 2)
        
        for (i in 1:2) {
            
            tip[i] <- checkVar(lapply(mylist, "[", i))
        
        }
    
    }

}
