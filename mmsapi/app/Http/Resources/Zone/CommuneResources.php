<?php

namespace App\Http\Resources\Zone;

use Illuminate\Http\Resources\Json\JsonResource;

class CommuneResources extends JsonResource
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
            'nom' => $this->nom,
            'user' => UserResource::collection($this->users),
        ];
    }
}
