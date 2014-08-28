
# o variabila categoriala v1 cu 105 de observatii, dintre care 5 de missing

mylist <- list(vars = list(v1 = c(2,2,4,3,4,99,1,2,2,1,2,2,3,1,3,99,4,1,4,4,2,4,3,1,3,97,4,1,2,1,4,1,4,99,3,1,4,3,4,4,3,3,4,3,4,2,4,3,4,3,2,2,1,2,2,3,4,4,1,4,2,1,99,4,3,2,4,2,4,3,4,2,3,2,3,3,4,4,3,2,4,4,2,4,4,4,4,3,2,4,2,3,1,2,3,2,3,4,3,2,3,2,4,4,3)),
               meta = list(v1 = c("Foarte putin"=1, "Putin"=2, "Mult"=3, "Foarte mult"=4, "Nu e cazul"=97, "Nu raspund"=99)))


rs <- function(x) {
    paste(rep(" ", x), collapse="")
}

tab <- function(x) {
    paste(rep("\t", x), collapse="")
}


if (length(mylist$vars) == 1) { # o singura variabila primita de la server
    
    if (length(mylist$meta) > 0) {
        
        # posibil variabila categoriala
        # insa si variabilele numerice pot avea etichete de valori (de missing)
        
        # verific daca numarul de valori unice din date este (cel mult) egal cu numarul de categorii din metadate
        if (length(unique(mylist$vars[[1]])) <= length(mylist$meta[[1]])) {
            
            # cu siguranta este o variabila categoriala
            valori <- as.vector(mylist$meta[[1]])
            etichete <- names(mylist$meta[[1]])
            frecventa <- as.vector(table(factor(mylist$vars[[1]], levels=valori, labels=etichete)))
            
            json <- paste(  "{\n",
                                rs(4), "\"success\": true,\n",
                                rs(4), "\"data\": [\n",
                                    rs(8), "{\n",
                                        rs(12), "\"itemtype\": \"table\",\n",
                                        rs(12), "\"title\": \"Tabel de frecvente pentru variabila: ", names(mylist$vars)[1], "\",\n",
                                        rs(12), "\"headerRow\": 1,\n",
                                        rs(12), "\"rows\": ", length(valori) + 1, ",\n",
                                        rs(12), "\"headerCol\": 1,\n",
                                        rs(12), "\"cols\": ", 3, ",\n",
                                        rs(12), "\"data\": [\n",
                                            rs(16), "[\"Nr.\", \"Categorie\", \"Frecventa\"],\n", sep="")
                                            for (i in seq(length(valori))) {
                                                json <- paste(json, rs(16), "[\"", valori[i],
                                                                            "\", \"", etichete[i],
                                                                            "\", \"", frecventa[i], "\"]",
                                                                            ifelse(i == length(valori), "", ","),
                                                                            "\n", sep="")
                                            }
                                        json <- paste(json, rs(12), "]\n",
                                    rs(8), "}\n",
                                rs(4), "]\n",    
                            "}\n", sep="")
            
        }
        else {
            
            # numarul de categorii din metadate este mai mic decat numarul unic de valori din date
            # posibil sa fie o variabila numerica de tip 1...10 cu doar doua etichete (la 1 in stanga si la 10 in dreapta)
            
        }
        
    }
    else {
        
        # e o variabila numerica, codul nu ar trebui sa ajunga aici decat foarte rar
        # intotdeauna ar trebui sa existe valori de missing undeva in metadate, chiar si pentru variabile numerice
        
    }
}
else { # nu mai mult de 2 variabile
    
}
