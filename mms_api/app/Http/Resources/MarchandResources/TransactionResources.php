<?php

namespace App\Http\Resources\MarchandResources;

use App\Models\Contrat;
use App\Models\Portefeuille;
use Carbon\Carbon;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\Auth;

class TransactionResources extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    { 
        if($this->contrat->client->user){

            return [
                'id' => $this->id,
                'montant' => $this->montant,
                'created_at' => Carbon::parse($this->created_at)->format('D M d-m-Y h:m:s') ,
                
                'contrat' => [
                    'id' => $this->contrat->id,
                    'numero_contrat' => $this->contrat->numero_contrat,
                    'client' => [
                        'id' => $this->contrat->client->id,
                        'profession' => $this->contrat->client->profession,
                        'employeur' => $this->contrat->client->employeur,
                        'user' => [
                            'id' => $this->contrat->client->user->id,
                            'nom' => $this->contrat->client->user->nom,
                            'prenom' => $this->contrat->client->user->prenom,
                            'sexe' => $this->contrat->client->user->sexe,
                            'telephone' => $this->contrat->client->user->telephone,
                            'adresse' => $this->contrat->client->user->adresse, 
                        ],
                    ],
                ],  
    
            ];
        }
        
    }
}
