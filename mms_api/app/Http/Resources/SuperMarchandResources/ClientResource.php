<?php

namespace App\Http\Resources\SuperMarchandResources;
use App\Http\Resources\InfoResource;
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
            'employeur' => $this->employeur,
            'profession' => $this->profession,
            'information_personnelle' =>  new InfoResource($this->user),
            'assures' => $this->assures,

        ];
    }
}
