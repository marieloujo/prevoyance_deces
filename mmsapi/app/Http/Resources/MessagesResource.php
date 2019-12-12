<?php

namespace App\Http\Resources;

use Carbon\Carbon;
use Illuminate\Http\Resources\Json\JsonResource;

class MessagesResource extends JsonResource
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
            'notification' => $this->notification == 1 ? true : false ,    
            'created_at' => Carbon::parse($this->created_at)->format('Y-m-d h:m:s'),     
            
        ];
    }
}
