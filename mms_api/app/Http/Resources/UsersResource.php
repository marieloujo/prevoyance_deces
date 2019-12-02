<?php

namespace App\Http\Resources;

use App\Http\Resources\CommuneResource;
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
            'actif' => $this->actif,
            'email' => $this->email,
            'usereable_type' => $this->usereable_type,
            'usereable_data' => $this->usereable,
            'commune' => new CommuneResource($this->commune),
        ];
    }
}
