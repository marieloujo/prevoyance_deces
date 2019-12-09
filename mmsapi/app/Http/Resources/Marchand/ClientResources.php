<?php

namespace App\Http\Resources\Marchand;

use Illuminate\Http\Resources\Json\JsonResource;

class ClientResources extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        if ($this->client->user) {
            
            return [
                'id' => $this->client->id,
                'profession' => $this->client->profession, 
                'user' => [
                    'id' => $this->client->user->id,
                    'nom' => $this->client->user->nom,
                    'prenom' =>$this->client->user->prenom,
                    'sexe' =>$this->client->user->sexe,
                    'telephone' =>$this->client->user->telephone,
                    'adresse' =>$this->client->user->adresse, 
                ],
            ];
        }
    }
}
