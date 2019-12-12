<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class UsersResource extends JsonResource
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
            'nom' => $this->nom,
            'prenom' => $this->prenom,
            'sexe' => $this->sexe,
            'telephone' => $this->telephone,
            'adresse' => $this->adresse,
            'actif' => $this->actif == 1 ? true : false ,
            'prospect' => $this->prospect == 1 ? true : false ,
            'situation_matrimoniale' => $this->situation_matrimoniale,
            'date_naissance' => $this->date_naissance,
            'email' => $this->email,
            'userable_id' => $this->userable_id,
            'userable_type' => $this->userable_type,
            'userable' => $this->userable,
            'commune' => [
                'id' => $this->commune->id,
                'nom' => $this->commune->nom,
                'departement' => [
                    'id' => $this->commune->departement->id,
                    'nom' => $this->commune->departement->nom,
                ] 
            ]
        ];
    }
}
