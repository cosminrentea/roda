FOLDERID=6
curl -H "Content-Type: multipart/mixed" -F "folderid=${FOLDERID}"  -F "content=@curl-cmsfile.sh; type=image/jpg" -X POST http://localhost:8181/roda/admin/cmsfilesave -i -v