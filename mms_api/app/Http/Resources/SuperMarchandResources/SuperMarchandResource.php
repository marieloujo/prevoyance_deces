<?php

namespace App\Http\Resources\SuperMarchandResources;

use App\Http\Resources\InfoResource;
use App\Http\Resources\CompteResource;
use App\Http\Resources\SuperMarchandResources\MarchandResource;
use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResource extends JsonResource
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
            // 'supermarchand' =>new InfoResource($this->super_marchand->user),
            'marchands' => MarchandResource::collection($this->marchands),
            'comptes' => CompteResource::collection($this->comptes),
        ];
    }
}

