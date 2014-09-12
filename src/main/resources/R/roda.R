
# Ex. 1: o variabila categoriala v1 cu 105 de observatii, dintre care 5 de missing

# mylist <- list(vars = list(v1 = c(2,2,4,3,4,99,1,2,2,1,2,2,3,1,3,99,4,1,4,4,2,4,3,1,3,97,4,1,2,1,4,1,4,99,3,1,4,3,4,4,3,3,4,3,4,2,4,3,4,3,2,2,1,2,2,3,4,4,1,4,2,1,99,4,3,2,4,2,4,3,4,2,3,2,3,3,4,4,3,2,4,4,2,4,4,4,4,3,2,4,2,3,1,2,3,2,3,4,3,2,3,2,4,4,3)),
#                meta = list(v1 = c("Foarte putin"=1, "Putin"=2, "Mult"=3, "Foarte mult"=4, "Nu e cazul"=97, "Nu raspund"=99)))


# Ex. 2: o singura variabila numerica cu 135 observatii

# mylist <- list(vars=list(v1 = sample(1:100, 135, replace=T)), 
#                meta=list(v1=c()))


# Ex. 3: o singura variabila ordinala pe o scala de raspuns 1...7, care poate fi interpretata si numeric

# mylist <- list(vars = list(v1 = c(97, 99, sample(1:7, 122, replace=T), 99)),
#                meta = list(v1 = c("Foarte putin"=1, "Foarte mult"=7, "Nu e cazul"=97, "Nu stiu"=98, "Nu raspund"=99)))


# Ex. 4: doua variabile categoriale

# mylist <- list(vars = list(v1 = sample(1:2, 100, replace=T), v2 = sample(1:2, 100, replace=T)),
#                meta = list(c("Urban"=1, "Rural"=2, "Nu e cazul"=97, "Nu stiu"=98, "Nu raspund"=99),
#                            c("Barbati"=1, "Femei"=2, "Nu e cazul"=97, "Nu stiu"=98, "Nu raspund"=99)))


# Ex. 5: doua variabile numerice

# mylist <- list(vars = list(v1 = c(97, 99, sample(1:10, 122, replace=T), 99), v2 = c(NA, sample(18:90, 123, replace = TRUE), 999)),
#                meta = list(v1 = c("NR/NS"=99, "Nu e cazul"=97), v2 = c("Non raspuns"=999)))


getStats <- function(mylist) {
    
    # aceasta comanda poate sta aici (caz in care se incarca la fiecare apelare din Java
    # sau poate fi specificata doar la deschiderea R, in fisierul .Rprofile din /home/user
    load("miss.Rdata")
    
    
    rs <- function(x) {
        paste(rep(" ", x), collapse="")
    }
    
    eroare <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"paragraph\",\n",
                            rs(12), "\"title\": \"Eroare\",\n",
                            rs(12), "\"content\": \"Nu se pot realiza statistici decat cu variabile categoriale si/sau numerice\"\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
    
    
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
                    
                    return("categoriala")
                    
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
            
            if (is.numeric(mylist$vars[[1]])) {
                
                return("numerica")
                
            }
            else {
                
                return("string")
                
            }
            
        }
        
    }
    
    
    getValEt <- function(x) {
        
        valori <- etichete <- sort(unique(mylist$vars[[x]]))
            
        if (length(mylist$meta[[x]]) > 0) {
            
            # se inlocuiesc celelalte etichete in afara de missing
            for (i in seq(length(mylist$meta[[x]]))) {
                
                pozitie <- match(mylist$meta[[x]][i], valori)
                
                if (!is.na(pozitie)) {
                    
                    etichete[pozitie] <- names(mylist$meta[[x]])[i]
                    
                }
                
            }
            
            # se adauga (daca exista) alte etichete pentru valori care nu exista in date
            diferente <- setdiff(mylist$meta[[x]], valori)
            
            if (length(diferente) > 0) {
                
                valori <- c(valori, diferente)
                
                etichete <- c(etichete, names(mylist$meta[[1]])[mylist$meta[[1]] %in% diferente])
                
                etichete <- etichete[order(valori)]
                
                valori <- sort(valori)
                
            }
            
        }
            
        return(list(valori, etichete))
    }
    
    
    ord1 <- function(mylist) {
        
        valet <- getValEt(1)
        
        valori <- valet[[1]]
        etichete <- valet[[2]]
        
        
        frecventa <- as.vector(table(factor(mylist$vars[[1]], levels=valori, labels=etichete)))
        
        json <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Tabel de frecvente pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(valori) + 2, ",\n",
                            rs(12), "\"cols\": ", 2, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"Categorie\", \"Frecventa\"],\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "[\"", paste(valori[i], etichete[i], sep=". "), "\", \"", frecventa[i], "\"],\n", sep="")
                                }
                                json <- paste(json, rs(16), "[\"\", \"Total\", \"", sum(frecventa), "\"]\n",
                            rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Diagrama bara pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"charttype\": \"bar\",\n",
                            rs(12), "\"height\": ", 35*length(valori), ",\n",
                            rs(12), "\"data\": [\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "{\n",
                                                rs(20), "\"name\": \"", etichete[length(valori) + 1 - i], "\",\n",
                                                rs(20), "\"value\": ", frecventa[length(valori) + 1 - i], "\n",
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
        histograma <- hist(mylist$vars[[1]], plot = FALSE)
        
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
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Histograma variabilei: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"charttype\": \"histogram\",\n",
                            rs(12), "\"height\": 500,\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "{\n",
                                    rs(20), "\"breaks\": [\"", paste(histograma$breaks, collapse = "\", \""), "\"],\n",
                                    rs(20), "\"counts\": [\"", paste(histograma$counts, collapse = "\", \""), "\"]\n",
                                rs(16), "}\n",
                            rs(12), "]\n",
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
        
        frecventa <- as.vector(table(factor(mylist$vars[[1]], levels=valori, labels=etichete)))
        
        etichete[!etichete %in% names(mylist$meta[[1]])] <- ""
        
        
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
                            rs(12), "\"rows\": ", length(valori) + 2, ",\n",
                            rs(12), "\"cols\": ", 2, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"Categorie\", \"Frecventa\"],\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "[\"", paste(valori[i] , etichete[i], sep=". "), "\", \"", frecventa[i], "\"],\n", sep="")
                                }
                                json <- paste(json, rs(16), "[\"Total\", \"", sum(frecventa), "\"]\n",
                            rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Diagrama bara pentru variabila: ", names(mylist$vars)[1], "\",\n",
                            rs(12), "\"charttype\": \"bar\",\n",
                            rs(12), "\"height\": ", 35*length(valori), ",\n",
                            rs(12), "\"data\": [\n", sep="")
                                for (i in seq(length(valori))) {
                                    json <- paste(json, rs(16), "{\n",
                                                rs(20), "\"name\": \"", etichete[length(valori) + 1 - i], "\",\n",
                                                rs(20), "\"value\": ", frecventa[length(valori) + 1 - i], "\n",
                                            rs(16), ifelse(i == length(valori), "}\n", "},\n"), sep="")
                                }
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
        return(json)
    }
    
    
    ord2 <- function(mylist) {
        
        etichete <- valori <- vector(mode = "list", length = 2)
        
        for (variabila in 1:2) {
        
            valet <- getValEt(variabila)
            valori[[variabila]] <- valet[[1]]
            etichete[[variabila]] <- valet[[2]]
            
            ismiss <- toupper(etichete[[variabila]]) %in% miss
            
            if (any(ismiss)) {
                
                valmis <- valori[[variabila]][ismiss]
                
                mylist$vars[[variabila]][mylist$vars[[variabila]] %in% valmis] <- NA
                
                valori[[variabila]] <- valori[[variabila]][!ismiss]
                etichete[[variabila]] <- etichete[[variabila]][!ismiss]
                
            }
        }
        
        
        frecventa <- table(factor(mylist$vars[[1]], levels=valori[[1]], labels=etichete[[1]]),
                           factor(mylist$vars[[2]], levels=valori[[2]], labels=etichete[[2]]))
        
        
        
        json <- paste("{\n",
                    rs(4), "\"success\": true,\n",
                    rs(4), "\"data\": [\n",
                        rs(8), "{\n",
                            rs(12), "\"itemtype\": \"table\",\n",
                            rs(12), "\"title\": \"Tabel incrucisat pentru variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                            rs(12), "\"headerRow\": 1,\n",
                            rs(12), "\"headerCol\": 1,\n",
                            rs(12), "\"rows\": ", length(valori[[1]]) + 2, ",\n",
                            rs(12), "\"cols\": ", length(valori[[2]]) + 2, ",\n",
                            rs(12), "\"data\": [\n",
                                rs(16), "[\"", paste(names(mylist$vars), collapse= "\\"),"\", ",
                                               paste(paste("\"", paste(valori[[2]], etichete[[2]], sep=". "), "\"", sep=""), collapse = ", "),
                                               ", \"Total\"],\n", sep="")
                                for (i in seq(length(valori[[1]]))) {
                                    json <- paste(json, rs(16), "[\"", paste(valori[[1]][i], etichete[[1]][i], sep=". "),
                                                  "\", \"", paste(frecventa[i, ], collapse = "\", \""), 
                                                  "\", \"", sum(frecventa[i, ]), "\"],\n", sep="")
                                }
                                json <- paste(json, rs(16), "[\"Total\", \"", paste(colSums(frecventa), collapse="\", \""), "\", \"", sum(frecventa), "\"]\n", 
                            rs(12), "]\n",
                        rs(8), "},{\n",
                            rs(12), "\"itemtype\": \"chart\",\n",
                            rs(12), "\"title\": \"Diagrama bara pentru variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                            rs(12), "\"charttype\": \"stackedbar\",\n",
                            rs(12), "\"catfield\": \"name\",\n",
                            rs(12), "\"datafields\": [\"", paste(colnames(frecventa), collapse="\", \""), "\"],\n", sep="")
                            
                            json <- paste(json, rs(12), "\"height\": ", 35*length(valori[[1]]), ",\n",
                            rs(12), "\"data\": [\n", sep="")
                            for (i in seq(nrow(frecventa))) {
                                json <- paste(json, rs(16), "{\n", rs(20), "\"name\": \"", rownames(frecventa)[nrow(frecventa) + 1 - i], "\",\n", sep="")
                                for (j in seq(ncol(frecventa))) {
                                    json <- paste(json, rs(20), "\"", colnames(frecventa)[j], "\": ", frecventa[nrow(frecventa) + 1 - i, j],
                                                  ifelse(j == ncol(frecventa), "\n", ",\n"), sep="")
                                    
                                }
                                json <- paste(json, rs(16), ifelse(i == length(etichete[[1]]), "}\n", "},\n"), sep="")
                            } 
                            json <- paste(json, rs(12), "]\n",
                        rs(8), "}\n",
                    rs(4), "]\n",    
                "}\n", sep="")
        return(json)
        
        
        
        
    }
    
    
    
    num2 <- function(mylist) {
        
        for (variabila in 1:2) {
            
            # se inlocuiesc valorile de missing (daca exista) cu NA
            if (length(mylist$meta[[variabila]]) > 0) {
                ismiss <- toupper(names(mylist$meta[[variabila]])) %in% miss
                if (any(ismiss)) {
                    mylist$vars[[variabila]][mylist$vars[[variabila]] %in% mylist$meta[[variabila]][ismiss]] <- NA
                }
            }
            
        }
        
        temp <- cbind(as.numeric(mylist$vars[[1]]), as.numeric(mylist$vars[[2]]))
        
        temp <- temp[apply(temp, 1, function(x) all(!is.na(x))), ]
        
        numerice <- round(apply(temp, 2, summary), 1)
        
        tempcor <- cor(temp[, 1], temp[, 2])
        
        
        json <- paste("{\n",
            rs(4), "\"success\": true,\n",
            rs(4), "\"data\": [\n",
                rs(8), "{\n",
                    rs(12), "\"itemtype\": \"table\",\n",
                    rs(12), "\"title\": \"Masuri numerice pentru variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                    rs(12), "\"headerRow\": 1,\n",
                    rs(12), "\"headerCol\": 1,\n",
                    rs(12), "\"rows\": ", nrow(numerice) + 1, ",\n",
                    rs(12), "\"cols\": ", 3, ",\n",
                    rs(12), "\"data\": [\n",
                        rs(16), "[\"\", \"", paste(names(mylist$vars), collapse = "\", \""), "\"],\n", sep="")
                        for (i in seq(nrow(numerice))) {
                            json <- paste(json, rs(16), "[\"", rownames(numerice)[i],
                                          "\", \"", paste(numerice[i, ], collapse="\", \""),
                                          ifelse(i == nrow(numerice), "\"]\n", "\"],\n"), sep="")
                        }
                    json <- paste(json, rs(12), "]\n",
                rs(8), "},{\n",
                    rs(12), "\"itemtype\": \"table\",\n",
                    rs(12), "\"title\": \"Corelatia dintre variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                    rs(12), "\"headerRow\": 1,\n",
                    rs(12), "\"headerCol\": 1,\n",
                    rs(12), "\"rows\": ", 2, ",\n",
                    rs(12), "\"cols\": ", 2, ",\n",
                    rs(12), "\"data\": [\n",
                        rs(16), "[\"\", \"", names(mylist$vars)[2], "\"],\n",
                        rs(16), "[\"", names(mylist$vars)[1], "\", \"", round(tempcor, 3), "\"]\n",
                    rs(12), "]\n",
                rs(8), "},{\n",
                    rs(12), "\"itemtype\": \"chart\",\n",
                    rs(12), "\"title\": \"Diagrama de imprastiere pentru variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                    rs(12), "\"charttype\": \"scatterplot\",\n",
                    rs(12), "\"height\": ", 500, ",\n",
                    rs(12), "\"data\": [\n",
                        rs(16), "[\"", paste(temp[, 1], collapse="\", \""), "\"],\n",
                        rs(16), "[\"", paste(temp[, 2], collapse="\", \""), "\"]\n",
                    rs(12), "]\n",
                rs(8), "}\n",
            rs(4), "]\n",    
        "}\n", sep="")
        
        return(json)
        
    }
    
    
    
    numord2 <- function(mylist, tip) {
        
        categ <- which(tip == "categoriala")
        
        # valori <- etichete <- sort(unique(mylist$vars[[categ]]))
        
        valet <- getValEt(categ)
        valori <- valet[[1]]
        etichete <- valet[[2]]
        
        ismiss <- toupper(etichete) %in% miss
        
        
        if (any(ismiss)) {
            
            valmis <- valori[ismiss]
            
            mylist$vars[[categ]][mylist$vars[[categ]] %in% valmis] <- NA
            
            valori <- valori[!ismiss]
            etichete <- etichete[!ismiss]
            
        }
        
        
        # se inlocuiesc valorile de missing (daca exista) cu NA
        if (length(mylist$meta[[3 - categ]]) > 0) {
            ismiss <- toupper(names(mylist$meta[[3 - categ]])) %in% miss
            if (any(ismiss)) {
                mylist$vars[[3 - categ]][mylist$vars[[3 - categ]] %in% mylist$meta[[3 - categ]][ismiss]] <- NA
            }
        }
        
        temp <- cbind(mylist$vars[[categ]], as.numeric(mylist$vars[[3 - categ]]))
        
        temp <- temp[apply(temp, 1, function(x) all(!is.na(x))), ]
        
        numerice <- round(apply(temp, 2, summary), 1)
        
        numerice <- matrix(NA, nrow=6, ncol=length(etichete))
        
        tempnum <- NA
        for (i in seq(length(etichete))) {
            tempnum <- summary(temp[temp[, 1] == valori[i], 2])
            numerice[, i] <- tempnum
        }
        
        rownames(numerice) <- names(tempnum)
        colnames(numerice) <- etichete
        
        
        json <- paste("{\n",
            rs(4), "\"success\": true,\n",
            rs(4), "\"data\": [\n",
                rs(8), "{\n",
                    rs(12), "\"itemtype\": \"table\",\n",
                    rs(12), "\"title\": \"Masuri numerice pentru variabilele: ", paste(names(mylist$vars), collapse= " si "), "\",\n",
                    rs(12), "\"headerRow\": 1,\n",
                    rs(12), "\"headerCol\": 1,\n",
                    rs(12), "\"rows\": ", nrow(numerice) + 1, ",\n",
                    rs(12), "\"cols\": ", 3, ",\n",
                    rs(12), "\"data\": [\n",
                        rs(16), "[\"\", \"", paste(etichete, collapse = "\", \""), "\"],\n", sep="")
                        for (i in seq(nrow(numerice))) {
                            json <- paste(json, rs(16), "[\"", rownames(numerice)[i],
                                          "\", \"", paste(numerice[i, ], collapse="\", \""),
                                          ifelse(i == length(numerice), "\"]\n", "\"],\n"), sep="")
                        }
                    json <- paste(json, rs(12), "]\n",
                rs(8), "}\n",
            rs(4), "]\n",    
        "}\n", sep="")
        
        return(json)
        
    }
    
    
    # verificarea tipurilor de variabile, si apelarea functiei corespunzatoare
    
    if (length(mylist$vars) == 1) { # o singura variabila primita de la server
        
        vartype <- checkVar(mylist)
        
        if (vartype == "categoriala") {
            
            return(ord1(mylist))
            
        }
        else if (vartype == "numord") {
            
            return(numord1(mylist))
            
        }
        else if (vartype == "numerica") {
            
            return(num1(mylist))
            
        }
        else {
            
            return(eroare)
            
        }
    }
    else { # nu mai mult de 2 variabile
        
        tip <- rep("", 2)
        
        for (i in 1:2) {
            
            tip[i] <- checkVar(lapply(mylist, "[", i))
        
        }
        
        if (all(tip == "categoriala")) {
            
            return(ord2(mylist))
            
        }
        else if (all(tip == "numerica")) {
            
            return(num2(mylist))
            
        }
        else if (all(tip %in% c("categoriala", "numerica"))) {
            
            return(numord2(mylist, tip))
            
        }
        else {
            
            return(eroare)
            
        }
    
    }

}
