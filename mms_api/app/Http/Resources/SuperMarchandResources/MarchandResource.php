<?php

namespace App\Http\Resources\SuperMarchandResources;

use App\Http\Resources\InfoResource;
use App\Http\Resources\CompteResource;
use App\Http\Resources\SuperMarchandResources\ClientResource;
use Illuminate\Http\Resources\Json\JsonResource;

class MarchandResource extends JsonResource
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
            'matricule' => $this->matricule,
            'commission' => $this->commission,
            'crédit_virtuel' => $this->credit_virtuel,
            'information_personnelle' => new InfoResource($this->user),
            'clients' => ClientResource::collection($this->clients),
            'comptes' => CompteResource::collection($this->comptes),
        ];
    }
}

