<?php

namespace App\Http\Resources\Client;
use App\Http\Resources\Assurer\AssurerResource;
use App\Http\Resources\Marchand\MarchandResources;
use App\Http\Resources\DocumentResource;
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
            'information_personnelle' =>  new UserResource($this->user),
            'marchand' => new MarchandResources($this->marchand),
         
            'assures' => AssurerResource::collection($this->assures),
        ];
    }
}
