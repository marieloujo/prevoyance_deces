<?php

namespace App\Http\Resources\Departement;

use App\Http\Resources\Departement\CommuneResources;
use Illuminate\Http\Resources\Json\JsonResource;

class UsersDepartementResource extends JsonResource
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
            'communes' => CommuneResources::collection($this->communes),
        ];
    }
}
