# NOTE: this script works only in "devel" profile


# first, set the CMS Folder ID (the folder where the file will be saved)
FOLDERID=1 
curl -u user:user -H "Content-Type: multipart/mixed" -F "folderid=${FOLDERID}"  -F "content=@curl-cmsfile.sh; type=text/x-shellscript" -X POST http://localhost:8080/roda/userjson/cmsfilesave -i -v


# if response is similar to this:
#
# {"id":61,"message":"CMS File created successfully","success":true}
#
# then 
# -- the uploaded file has the ID = 61 (usable in other calls) 
# -- the file can be downloaded from:  http://localhost:8080/roda/cmsfilecontent/61