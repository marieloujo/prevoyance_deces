<?php

namespace App\Http\Resources\SuperMarchand;

use App\Http\Resources\ContratResources;
use Illuminate\Http\Resources\Json\JsonResource;

class UserResources
 extends JsonResource
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
            'contrat' => ContratResources::collection($this->usereable->contrats),
        ];
    }
}

