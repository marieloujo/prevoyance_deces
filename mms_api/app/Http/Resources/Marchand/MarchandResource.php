<?php

namespace App\Http\Resources\Marchand;

use App\Http\Resources\UserResource;
use App\Http\Resources\CompteResource;
use App\Http\Resources\ClientResource;
use App\Http\Resources\SuperMarchandResource;
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
            'crédit_virtuel' => $this->crédit_virtuel,
            'user' => new UserResource($this->user),
            'supermarchand' =>new SuperMarchandResource($this->super_marchand),
            'clients' => ClientResource::collection($this->clients),
            'comptes' => CompteResource::collection($this->comptes),
        ];
    }
}

