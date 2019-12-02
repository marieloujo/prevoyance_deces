<?php

namespace App\Http\Resources\Departement;

use App\Http\Resources\Departement\UserResources;
use Illuminate\Http\Resources\Json\JsonResource;

class CommunesResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    //list des utilisateurs par commune
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'nom' => $this->nom,
        ];
    }
}
