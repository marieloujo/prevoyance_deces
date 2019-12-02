<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class UserResource extends JsonResource
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
            'body' => $this->body,
            'read_at' => Carbon::parse( $this->read_at)->format('d/m/Y'),
            'created_at' => Carbon::parse( $this->created_at)->format('d/m/Y'),
            
        ];
    }
}

