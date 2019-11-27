<?php

namespace App\Http\Resources;

use App\Http\Resources\DepartementResource;

use Illuminate\Http\Resources\Json\JsonResource;

class CommuneResource extends JsonResource
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
            'departement' => new DepartementResource($this->departement),
        ];
    }
}
