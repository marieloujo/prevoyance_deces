Route 


	/user :    Route::get('user','Api\UserController@getAuthenticatedUserable');
		    
	/logout:   Route::post('logout','Api\Auth\LoginController@logout');

	/marchands/{marchand}/clients : get's clients by marchand '{marchand} id of marchand'
	/marchands/{marchand}/clients/{client}/contrats : get client contrats by marchand '{marchand} id of marchand {client} id of client'

	/marchands/{marchand}/transactions : get marchand transactions of semaine '{marchand} id of marchand'
	/marchands/{marchand}/transactions/{date?} : get marchand transactions for date '{marchand} id of marchand, transactions of {date?}'
	/marchands/{marchand}/getAllTransactions : get's all transactions of marchand '{marchand} id of marchand' [return contrat and client of transactions]
	/marchands/{marchand}/getCompte : get principal account of marchand '{marchand} id of marchand' [return credit_virtuel]
	/marchands/{marchand}/getComptes : get's all accounts of marchand '{marchand} id of marchand' [return comptes]
	/departements/{departement}/marchands : get's all marchands of departement '{departement} id of departement' [return commune and users]
	
	/clients/{client}/lastContrats : get's 10 last actif contrats of client '{client} id of client' [return contrats, assures,marchand]
	/clients/{client}/contrats : get's first all actif contrats of client after non actif contrats '{client} id of client' [return contrats, assures,marchand]


	/login : connexion route
	/register : Register route

apiResource : CRUD of model

	/models/		GET ALL RECORD
	/models/		POsT CREATE RECORD
	/models/idModel		GET RECORD BY ID
	/models/idModel/update  UPDATE RECORD BY ID
	/models/idModel		DELETE RECORD BY ID

NB:
	All get collection request is paginate;
	Return of all client request is on response json ( exemple : success[ => ['message' => 'message']] or success[ => ['data' => resource']] oor errors[ => ['message' => 'message']])
