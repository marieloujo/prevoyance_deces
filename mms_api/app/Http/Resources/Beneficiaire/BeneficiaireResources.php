<?php

namespace App\Http\Resources\Beneficiaire;

use App\Http\Resources\UserResource;
use Illuminate\Http\Resources\Json\JsonResource;

class BeneficiaireResources extends JsonResource
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
            'user' => new UserResource($this->user),
            
        ];
    }
}
