<?php

namespace App\Http\Resources\SuperMarchand;

use App\Http\Resources\UserResource;
use App\Http\Resources\Marchand\ContratResources;
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
            'credit_virtuel' => $this->credit_virtuel,
            'commission' => $this->commission,
            'user' => new UserResource($this->user),
            'contrats' => ContratResources::collection($this->contrats),
        ];
    }
}
