Ext.define('RODAdmin.store.studies.CBEditor.SRights', {
    extend: 'Ext.data.Store',
 	fields: ['id', 'level', 'description'],
	 data: [{
     	   "id": 1,
            "level": "0",
            "description" : "Free access and download for any type of user, whether registrated or not, whether academic or non-academic. This category will contain totally public datasets, over which the owners have not imposed any restriction.",
    }, {
        "id": 2,
        "level": "A",
        "description" : "Free access to the datasets for academic users (category I). The users will only have to pay the costs related the storage media used for sending the data set (e.g. floppy disk, CD etc.); RODA may distribute the datasets without asking for the owner’s approval."
    }, {
        "id": 3,
        "level": "B",
        "description" : "Data access restricted by the owner's written approval, whereby the owner gets involved in the data set access control activity, taking the final decision as to any person's access to the data set."
    }, {
        "id": 4,
        "level": "C",
        "description" : "Owner-restricted access based on a confidentiality contract; the data set can only be accessed against payment by the user of a certain amount specified by the owner." 	
    }
    ]
});
