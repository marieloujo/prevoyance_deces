<?php

namespace App\Http\Resources\Collection;

use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Carbon;

class PortefeuilleResource extends JsonResource
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
            'montant' => $this->montant,
            'created_at' => Carbon::parse($this->created_at)->format('Y-m-d h:m:s'),
        ];
    }
}
