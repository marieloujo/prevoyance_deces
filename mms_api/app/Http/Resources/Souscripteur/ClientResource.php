<?php

namespace App\Http\Resources\Souscripteur;
use App\Http\Resources\ContratResources;
use App\Http\Resources\ContratResource;

use Illuminate\Http\Resources\Json\JsonResource;

class ClientResource extends JsonResource
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
            'contrats' =>  ContratResource::collection($this->contrats),
        ];
    }
}

