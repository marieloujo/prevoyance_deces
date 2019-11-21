<?php

namespace App\Http\Resources\ClientResources;

use App\Http\Resources\InfoResource;
use Illuminate\Http\Resources\Json\JsonResource;

class MarchandResource extends JsonResource
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
            'information_personnelle' => new InfoResource($this->user),
        ];
    }
}
