<?php

namespace App\Http\Resources\SuperMarchand;

use App\Http\Resources\UserResource;
use App\Http\Resources\CompteResource;
use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResourceDetails extends JsonResource
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
            'user' => new UserResource($this->user),
            'comptes' => CompteResource::collection($this->comptes),
        ];
    }
}
