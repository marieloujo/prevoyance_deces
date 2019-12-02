<?php

namespace App\Http\Resources\Client;

use App\Http\Resources\Client\ContratResources;
use App\Http\Resources\UserResource;
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
            'id' => $this->id,
            'profession' => $this->profession,
            'employeur' => $this->employeur,
            'user' => new UserResource($this->user),
            'contrats' => ContratResources::collection($this->contrats),
        ];
    }
}
