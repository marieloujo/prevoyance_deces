<?php

namespace App\Http\Resources\Marchand;
use App\Http\Resources\ContratResources;

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
            'contrats' => ContratResources::collection($this->contrats),
        ];
    }
}

