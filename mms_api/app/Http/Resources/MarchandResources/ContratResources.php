<?php

namespace App\Http\Resources\MarchandResources;

use App\Http\Resources\PortefeuilleResource;
use Illuminate\Http\Resources\Json\JsonResource;

class ContratResources extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'numero_contrat' => $this->numero_contrat,
            'numero_police_assurance' => $this->numero_police_assurance,
            'garantie' => $this->garantie,
            'prime' => $this->prime,
            'duree' => $this->duree,
            'date_debut' => $this->date_debut,
            'date_echeance' => $this->date_echeance,
            'date_effet' => $this->date_effet,
            'date_fin' => $this->date_fin,
            'fin' => $this->fin == 1 ? true : false ,
            'valider' => $this->valider == 1 ? true : false ,
            'assure' =>[
                'id' => $this->assure->id,
                'profession' => $this->assure->profession, 
                'user' => [
                    'id' => $this->assure->user->id,
                    'nom' => $this->assure->user->nom,
                    'prenom' =>$this->assure->user->prenom,
                    'sexe' =>$this->assure->user->sexe,
                    'telephone' =>$this->assure->user->telephone,
                    'adresse' =>$this->assure->user->adresse, 
                ],
            ],
            'portefeuilles' => [
                $this->portefeuilles,
            ]
        ];
    }
}
