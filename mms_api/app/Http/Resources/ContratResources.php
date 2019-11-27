<?php

namespace App\Http\Resources;

use App\Http\Resources\MarchandResources;
use App\Http\Resources\AssurerResources;
use App\Http\Resources\ClientResources;
use App\Http\Resources\UsersResource;


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
            'portefeuille' => $this->portefeuille,
            'date_debut' => $this->date_debut,
            'date_echeance' => $this->date_echeance,
            'date_effet' => $this->date_effet,
            'fin' => $this->fin,
            'valider' => $this->valider,
            'marchand' => new MarchandResources($this->marchand),
            'assure' =>new AssurerResources($this->assure->user),
            'client'  => new ClientResources($this->client->user),
            
        ];
    }
}

