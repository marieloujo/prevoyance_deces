<?php

namespace App\Http\Resources\SuperMarchand;

use Illuminate\Http\Resources\Json\JsonResource;

class MarchandResources extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        if ($this->user) {
            
            return [
                'id' => $this->id,
                'credit_virtuel' => $this->credit_virtuel,
                'commission' => $this->commission, 
                'user' => [
                    'id' => $this->user->id,
                    'nom' => $this->user->nom,
                    'prenom' =>$this->user->prenom,
                    'sexe' =>$this->user->sexe,
                    'telephone' =>$this->user->telephone,
                    'adresse' =>$this->user->adresse,
                    'commune' =>[
                        'id' =>$this->user->commune->id,
                        'nom' =>$this->user->commune->nom
                    ],
                ],
            ];
        }
    }
}
